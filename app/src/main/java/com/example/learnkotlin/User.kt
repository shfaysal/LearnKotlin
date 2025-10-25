package com.example.learnkotlin

import android.R
import com.example.learnkotlin.Student



//online course platform

open class User (
    val name : String,
    val email : String,
    val id : Int
)

class Instructor (
    name : String,
    email : String,
    id : Int
) : User(name, email, id) {


    fun createCourse(){
        println("create course")
    }
}


class Student(
    name : String,
    email : String,
    id : Int
) : User(name, email,id) {

    fun enrollCourse(){
        println("enroll course")
    }
}


class Course(
    val title : String,
    val instructor : Instructor,
    val student : List<Student>
) {

    fun showDetails(){
        println("title: $title")
        println("instructor: ${instructor.name}")
        student.forEach {
            println("student: ${it.name}")
        }
    }


}


fun main(){

    val student1 = Student(
        name = "John",
        email = "john.mclean@examplepetstore.com",
        id = 1
    )
    val student2 = Student(
        name = "Johnasd",
        email = "john.mclean@examplepetstore.com",
        id = 2
    )

    val student3 = Student(
        name = "Sazzad",
        email = "john.mclean@examplepetstore.com",
        id = 3
    )
    val student4 = Student(
        name = "Faysal",
        email = "john.mclean@examplepetstore.com",
        id = 4
    )

    val instructor = Instructor(
        name = "Teacher",
        email = "john.mclean@examplepetstore.com",
        id = 1
    )

    val baced = Instructor(
        name = "Baced",
        email = "john.mclean@examplepetstore.com",
        id = 2
    )

    val banglaCourse = Course(
        title = "Bangla",
        instructor = instructor,
        student = listOf(student1, student2)
    )

    val englishCourse = Course(
        title = "English",
        instructor = baced,
        student = listOf(student1, student2, student3, student4)
    )

    printCourseDetails(listOf(banglaCourse, englishCourse))
}


fun printCourseDetails(courses : List<Course>){
    courses.forEach {
        it.showDetails()
        println("---------------------------------")
    }
}


