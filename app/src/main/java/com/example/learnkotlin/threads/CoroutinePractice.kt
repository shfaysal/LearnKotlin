package com.example.learnkotlin.threads

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import kotlin.coroutines.suspendCoroutine

fun main(){

    // lazy work only when it's needed
//    val job1 = GlobalScope.launch (start = CoroutineStart.LAZY) {
//        delay(200)
//        println("Pong")
//        delay(200)
//    }
//
//    GlobalScope.launch {
//        delay(200)
//        println("Ping")
//        job1.join()
//        println("Ping")
//        delay(200)
//    }
//
//    Thread.sleep(1000)

    // parent child relationship
//managing job hierarchy

//    with(GlobalScope){
//        val parentJob = launch {
//            delay(200)
//            println("I am a parent")
//            delay(200)
//        }
//
//        launch (parentJob) {
//            delay(200)
//            println("I am a child")
//            delay(200)
//        }
//
//        launch (parentJob) {
//            delay(200)
//            println("I am a child2")
//            delay(200)
//        }
//
////        Thread.sleep(2000)
//        if (parentJob.children.iterator().hasNext()){
//            println("The job has children ${parentJob.children}")
//        }else {
//            println("The job has no children")
//        }
//        Thread.sleep(1000)
//    }

//    var isDoorOpen = false
//
//    println("Unlocking the door..please wait.\n")
//    GlobalScope.launch {
//        delay(3000)
//        isDoorOpen = true
//    }
//
//    GlobalScope.launch {
//        repeat(4){
//            println("Trying to open the door..")
//            delay(800)
//            if(isDoorOpen){
//                println("opened the door!")
//            }else {
//                println("The door is still locked")
//            }
//        }
//    }
//
//    Thread.sleep(5000)
//
//    println("Done")

//    GlobalScope.launch {
//        val bgThreadName = Thread.currentThread().name
//        println("I'm job 1 in thread $bgThreadName")
//        delay(200)
//        GlobalScope.launch (Dispatchers.Swing) {
//            val uiThreadName = Thread.currentThread().name
//            println("I'm job 2 in thread $uiThreadName")
//        }
//    }
//
//    Thread.sleep(1000)

    GlobalScope.launch {
        val user = getValue { "Testing continuation" }

        println(user)
    }

    println("main thread")

    Thread.sleep(2000)
}


//next elaborating continuations


suspend fun <T : Any> getValue(provider: () -> T): T =
    suspendCoroutine { continuation ->

        continuation.resumeWith(Result.runCatching { provider() })

    }

fun executeBackground(action: suspend () -> Unit) {
    GlobalScope.launch { action() }
}

fun executeMain(action: suspend  () -> Unit) {
    GlobalScope.launch(Dispatchers.Swing) { action() }
}















