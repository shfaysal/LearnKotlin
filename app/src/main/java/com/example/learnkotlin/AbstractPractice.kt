package com.example.learnkotlin

fun main(){

//    val circle = Circle()
//    val triangle = Triangle()
//
//    val objects = listOf(circle, triangle)
//
//    drageObject(objects)
//
//    val test1 = Test1()
//    val test2 = Test2()
//
//    val objects1 = listOf(test1, test2)
//
//    drageObject1(objects1)
//    val duck = Duck()
//    duck.eat()    // from Animal
//    duck.fly()    // from Flyable
//    duck.fly()
//    duck.swim()

    val test2 = Test1()
    test2.test()

}

open class Test {
    open fun test(){
        println("test")
    }
}
fun drageObject1(objects : List<Test>){
    for (obj in objects){
        obj.test()
    }
}

fun drageObject(objects : List<Shape>){
    for (obj in objects){
        obj.area()
    }
}

open class Test1 : Test(){
    override fun test(){
        super.test()
        println("test1")
    }
}

class Test2 : Test1(){
    override fun test(){
        super.test()
        println("test2")
    }
}

abstract class Shape {

    abstract fun area()

}

interface Test4{

}


class Circle : Shape(), Test4 {
    override fun area() {
//        TODO("Not yet implemented")
        println("area of circle")
    }


}

class Triangle : Shape() {
    override fun area() {
        println("area of triangle")
    }


}

interface Flyable {
    fun fly() = println("Flying")
}

interface Swimmable {
    fun fly() = println("Swimming")
    fun swim() = println("Swimming")
}

open class Animal {
    fun eat() = println("Eating")
}

// ✅ Kotlin allows:
class Duck : Animal(), Flyable, Swimmable {
    override fun fly() {
        super<Swimmable>.fly()
    }
}



