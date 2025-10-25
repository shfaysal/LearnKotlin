package com.example.learnkotlin.threads

fun main(){

    val thread = Thread {
        for (i in 1..5){
            println("Thread: $i name : ${Thread.currentThread().name}")
            Thread.sleep(500)
        }
    }

    thread.start()

    for(i in 1..5){
        println("Main thread: $i name : ${Thread.currentThread().name}")
        Thread.sleep(500)
    }
}