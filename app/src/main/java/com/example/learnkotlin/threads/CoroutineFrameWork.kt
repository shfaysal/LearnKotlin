package com.example.learnkotlin.threads

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.swing.Swing
import kotlin.coroutines.suspendCoroutine

suspend fun <T: Any> getValue1(provider: () -> T): T =
    suspendCoroutine { continuation ->

        continuation.resumeWith(Result.runCatching { provider() })
    }

fun executeBackground1(action: suspend () -> Unit) {
    GlobalScope.launch (Dispatchers.IO) {
        action()
    }
}

fun executeMain1(action: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Swing) { action() }
}

data class User(val id: String, val name: String)

fun getUserFromNetwork(userId : String) : User {
    println("Fetching user $userId from network")
    Thread.sleep(2000) // simulate slow call
    println("User fetched successfully!")
    return User(userId, "John Doe")
}


fun main(){
    //simulate UI starting the background task
    executeBackground1 {
        val user = getValue1 { getUserFromNetwork("123") }

        executeMain1 {
            println("Displaying user on UI: $user")
        }
    }

    executeMain1 {
        delay(1000)
        println("This is execute main 1 again")
    }

    println("main thread")

//    Thread.sleep(3000)
}



