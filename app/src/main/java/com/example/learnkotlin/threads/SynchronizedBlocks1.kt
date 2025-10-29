package com.example.learnkotlin.threads

class Counter {

    private var count = 0

    @Synchronized
    fun increment(){
        count++
    }

    fun getCount() = count
}


fun main(){

    val counter = Counter()

    val thread1 = Thread {
        repeat(1000){
            println("Thread 1: ${counter.getCount()}")
            counter.increment()
        }
    }


    val thread2 = Thread {
        repeat(1000){
            println("Thread 2: ${counter.getCount()}")
            counter.increment()
        }
    }


    thread1.start()
    thread2.start()

    thread1.join()
    thread2.join()

    println(counter.getCount())





//    thread1.forEach { it.start() }
//    thread2.forEach { it.start() }
//
//    thread1.forEach { it.join() }
//    thread2.forEach { it.join() }

//
//    println(counter.getCount())





}