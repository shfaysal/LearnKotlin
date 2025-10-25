package com.example.learnkotlin

import okhttp3.internal.toNonNegativeInt

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class MyAnnotation(val info : String)


@MyAnnotation("annotation practice")
class MyClass

fun main(){
//    val clazz = MyClass::class.java
//
//    val annotation = clazz.getAnnotation(MyAnnotation::class.java)
//
//    println(annotation?.info)

//    while (true){
//        println("Please enter a number")
//
//        val input = readln()
//
//        val inputAsInteger = input.toIntOrNull()
//
//        inputAsInteger?.let {
//            println("value = $inputAsInteger")
//        } ?: run {
//            println("Invalid input")
//        }
//
//
//    }

    val input = "hello how are you"

//    input[2] = 69

//    for (i in 0..input.lastIndexOff(100)) {
//        println(input[i])
//
//    }

    print(input.lastIndexOf("u"))



}