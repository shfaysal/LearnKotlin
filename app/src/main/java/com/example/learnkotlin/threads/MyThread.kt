package com.example.learnkotlin.threads

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


fun main(){
//    val t1 = MyThread()
//    t1.start()
//
//    // Main thread work
//    for (i in 1..10) {
//        println("Main thread: $i")
//        Thread.sleep(300)
//    }
//
//    println("Program ends")

//    runBlocking {
//        println("Starting in thread: ${Thread.currentThread().name}")
//        fetchDataAndUpdateUI()
//        println("Finished in thread: ${Thread.currentThread().name}")
//    }


    val lock = Any()
    var count = 0

    val threads = List(1000){
        Thread{
            synchronized(lock){
                count++
            }
        }
    }
    

    threads.forEach { it.start() }
    threads.forEach { it.join() }

    println(count)

}

class MyThread : Thread() {

    override fun run() {
        for (i in 1..10){
            println("My thread is running: $i")
            Thread.sleep(500)
        }
    }
}

suspend fun fetchDataAndUpdateUI(){

    // run background work
    val data = withContext(Dispatchers.IO){
        println("Fetching data in thread: ${Thread.currentThread().name}")
        Thread.sleep(3000)
        "Hellow from Server"
    }

    //back to main
    withContext(Dispatchers.Default){
        println("Updating UI in thread: ${Thread.currentThread().name}")
        println(data)
    }
}