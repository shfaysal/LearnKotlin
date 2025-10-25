package com.example.learnkotlin

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
suspend fun main(){


    val job = GlobalScope.launch {
        val child = launch {
            repeat(4){
                delay(1000)
                println("Child coroutine completed $it")
            }

        }

        delay(2000)
        println("Parent coroutine completed")
        cancel()
    }

//    delay(3000)
//    println("Parent coroutine cancelling")
//    job.cancel()
//    job.join()

//    println("Job Started")

    //cancel it after 2.5 seconds
//    delay(2000)
//    job.cancel()
//    println("Job cancelled")

}