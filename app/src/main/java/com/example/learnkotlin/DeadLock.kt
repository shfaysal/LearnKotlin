package com.example.learnkotlin

fun main(){

    val lockA = Any()
    val lockB = Any()


    val thread1 = Thread {
        synchronized(lockA){
            println("Thread 1: Holding lock A")

            Thread.sleep(100)

            println("Thread 1 : waiting to lock lockB")

            synchronized(lockB){
                println("Thread 1: Holding lock B")
            }

        }


    }

    val thread2 = Thread {
        synchronized(lockB){
            println("Thread 2: Holding lock B")

            Thread.sleep(100)

            println("Thread 2 : waiting to lock lockA")
            synchronized(lockA){
                println("Thread 2: Holding lock A")
            }
        }
    }

    thread1.start()
    thread1.join()


    thread2.start()
    thread2.join()

    println("Finished")

}