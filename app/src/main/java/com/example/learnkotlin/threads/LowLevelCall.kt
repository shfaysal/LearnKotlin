package com.example.learnkotlin.threads

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun getUser(id: String, callback: (User?, Throwable?) -> Unit) {
    Thread {
        try {
            Thread.sleep(500)
            throw Exception ("User not found1")
            callback(User(id, "John Doe"), null)
        } catch (e: Exception) {
            callback(null, e)
        }
    }.start()
}
data class Post(val id: String, val text: String)

fun getPosts(userId: String, callback: (List<Post>?, Throwable?) -> Unit) {
    Thread {
        try {
            Thread.sleep(500)
//            if (userId == "123") throw Exception("User not found")
//            Throwable ("User not found")
            callback(listOf(Post(userId, "Hello World")), null)
        } catch (e: Exception) {
            callback(null, e)
        }
    }.start()
}
data class Comment(val text: String)

fun getComments(postId: String, callback: (List<Comment>?, Throwable?) -> Unit) {
    Thread {
        try {
            Thread.sleep(500)
            callback(listOf(Comment("Nice post!")), null)
        } catch (e: Exception) {
            callback(null, e)
        }
    }.start()
}


suspend fun getUser(id: String): User {
    delay(500)
    return User(id, "Filip")
}

suspend fun getPosts(userId: String): List<Post> {
    delay(500)
    return listOf(Post("1", "Hello World"))
}

suspend fun getComments(postId: String): List<Comment> {
    delay(500)
    return listOf(Comment("Nice post!"))
}

fun main(){


//    runBlocking {
//        val user = getUser("123") {
//            user, error ->
//
//        }
//        val posts = getPosts(user.id)
//        val comments = getComments(posts.first().id)
//
//    }

    runBlocking {
        try {
            val user = getUser("123")
            println("User loaded: $user")
            val posts = getPosts(user.id)
            println("Posts loaded: $posts")
            val comments = getComments(posts.first().id)

            println("Done! ${comments.size} comments loaded.")
        }catch (e : Exception){
            println("❌ Error: ${e.message}")
        }
    }





//    getUser("123") { user, error ->
//        if (error == null && user != null) {
//            getPosts(user.id) { posts, error ->
//                if (posts != null && error == null){
//                    getComments(posts.first().id) { comments, error ->
//                        if (comments != null && error == null){
//                            println("✅ Done! ${comments.size} of ${user.id} comments loaded.")
//                        }else {
//                            println("❌ Error loading comments: ${error?.message}")
//                        }
//
//                    }
//
//                }else {
//                    println("❌ Error loading posts: ${error?.message}")
//                }
//            }
//        } else {
//            println("❌ Error loading user: ${error?.message}")
//        }
//    }

}














