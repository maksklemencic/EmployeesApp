package com.example.employeesapp

import com.example.employeesapp.classes.Employee
import com.example.employeesapp.classes.Gender
import org.junit.Assert.*
import org.junit.Test

class EmployeeTest {

    @Test
    fun `employee attributes are correctly assigned`() {
        val employee = Employee(
            id = 1,
            name = "John",
            lastName = "Doe",
            age = 30,
            gender = Gender.Male
        )

        // Assert that each property is correctly assigned
        assertEquals(1, employee.id)
        assertEquals("John", employee.name)
        assertEquals("Doe", employee.lastName)
        assertEquals(30, employee.age)
        assertEquals(Gender.Male, employee.gender)
    }

    @Test
    fun `employee name can be changed`() {
        val employee = Employee(
            id = 2,
            name = "Jane",
            lastName = "Doe",
            age = 28,
            gender = Gender.Female
        )

        employee.name = "Alice"

        assertEquals("Alice", employee.name)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `employee age must be positive`() {
        Employee(
            id = 3,
            name = "Negative",
            lastName = "Age",
            age = -1,
            gender = Gender.NotSpecified
        )
    }
}
