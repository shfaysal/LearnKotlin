package com.example.learnkotlin.threads

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

data class User1(val id: Int, val name: String)


suspend fun getUserFromRemote(id : Int) : User1 {
    println("user is called $id")

    if (id == 1){
        delay(1000)
    } else {
        delay(4000)
    }

//    delay(4000)

//    delay(2000)
    return User1(id, "User$id")
}


fun getUserByIdFromServer(userId: Int) : Deferred<User1> = GlobalScope.async {
    delay(3000)
    User1(userId, "John Doe")
}

fun main(){

    println("main starts")
    // async and wait using deferred
//    runBlocking {
//        val userId = 392
//        val user = getUserByIdFromServer(userId)
//        println("Waiting for future ${Thread.currentThread().name}")
//        val user1 = user.await()
//        println("User Received: $user1")
//        println("completed")
//    }

    runBlocking {
        val user1 = async(Dispatchers.Default) { getUserFromRemote(1) }
        val user2 = async(Dispatchers.Default) { getUserFromRemote(2) }

//        val user1 = async{ getUserFromRemote(1) }
//        val user2 = async { getUserFromRemote(2) }

        println("Both requests started at the same time")

//        delay(5000)
        Thread.sleep(5000)
//        delay(5000)
        println("waiting done")


        println("${user1.await()}")
        println("is awaiting")




//        Thread.sleep(3000)
//        println("waiting done")
//////
        println("${user2.await()}")
        println("user12 is waiting")



//        println("user1 ${user1.await()}, user2 ${user2.await()}")

//




//        if (user2.isCompleted){
//            println("user2 is completed")
//        }

//        wait for both to finish
//        val users = listOf(user1.await(), user2.await())
//
//
//        if (user1.isCompleted && user2.isCompleted){
//
//            println("Both requests completed at the same time")
////            println(users)
//        }
//        if (user2.isCompleted){
//            println("user1 is completed")
//        } else {
//            println("both are completed")
//        }



        println("Both requests completed at the same time")
//        println(users)
    }
}
























