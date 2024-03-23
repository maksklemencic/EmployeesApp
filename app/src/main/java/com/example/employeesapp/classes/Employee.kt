package com.example.employeesapp.classes

enum class Gender { Male, Female, NotSpecified }

data class Employee(
    val id: Int,
    var name: String,
    var lastName: String,
    var age: Int,
    val gender: Gender,
) {
    init {
        require(age >= 0) { "Age should not be negative" }
    }
}
