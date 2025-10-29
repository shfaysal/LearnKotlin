package com.example.learnkotlin.threads

val lock = Any()
var counter1 = 0
var counter2 = 0


fun main(){

    val thread1 = Thread {
        for(i in 1..1000){
            synchronized(lock){
                println("Thread 1: $counter1")
                counter1++
            }
        }
    }

    val thread2 = Thread {
        for(i in 1..1000){
//            Thread.sleep(1000)
            synchronized(lock){
                println("Thread 2: $counter2")
                counter2++
            }
        }
    }

    thread1.start()
    thread2.start()

//    thread1.join()
//    thread2.join()

    println("Final counter: $counter1 -- $counter2")

}