package com.example.learnkotlin

sealed class SetUp1(
    private val accountNumber : String,
    private val balance : Double,
) {

    class A11(val a : String, val b : Int) : SetUp1(accountNumber = a, balance = b.toDouble())
    class A22(val a : String, val b : Int) : SetUp1(accountNumber = a, balance = b.toDouble())

    fun deposit(amount : Double) : Double{
        return balance + amount
    }
}

fun main(){

    val a = SetUp1.A11("123", 100)
    val b = SetUp1.A22("456", 200)
    val list = listOf(a, b)

    printDeposit1(list)


}


fun printDeposit1(list : List<SetUp1>){
    list.forEach {
        when(it){
            is SetUp1.A11 -> println(it.deposit(100.0))
            is SetUp1.A22 -> println(it.deposit(200.0))
            else -> ""
        }
    }
}