package com.example.learnkotlin

fun main(){

    val a = A1("123", 100)
    val b = A2("456", 200)
    val list = listOf(a, b)

    printDeposit(list)

//    list.forEach {
//        println(it.deposit(32.9))
//    }

}

open class Setup(
    private val accountNumber : String,
    private val balance : Double,
){
    fun deposit(amount : Double) : Double{
        return balance + amount
    }

    fun withdraw(amount : Double) {
        print("withdrawing")
    }

    fun get_balance() = balance

    fun checkValidation(){
        println("checking validation")
    }
}

fun printDeposit(list : List<Setup>){
    list.forEach {
        when(it){
            is A1 -> println(it.deposit(100.0))
            is A2 -> println(it.deposit(200.0))
        }
    }
}


class A1(val a : String, val b : Int) : Setup(accountNumber = a, balance = b.toDouble()) {

}

class A2(val a : String, val b : Int) : Setup(accountNumber = a, balance = b.toDouble()) {

}
