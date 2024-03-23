package com.example.employeesapp

import com.example.employeesapp.ui.screens.EmployeeListScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.employeesapp.classes.Employee
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val sharedPreferences = getSharedPreferences("EmployeesApp", Context.MODE_PRIVATE)
            val employees = remember { mutableStateListOf<Employee>().apply {
                addAll(loadEmployees(sharedPreferences))
            }}

            EmployeeListScreen(
                employees = employees,
                onDeleteEmployee = { employee ->
                    employees.remove(employee)
                    saveEmployees(sharedPreferences, employees)
                },
                saveEmployees = ::saveEmployees,
                sharedPreferences = sharedPreferences
            )

            DisposableEffect(Unit) {
                onDispose {
                    saveEmployees(sharedPreferences, employees)
                }
            }
        }


        if (Build.VERSION.SDK_INT >= 21) {

            WindowCompat.setDecorFitsSystemWindows(window, true)
            WindowInsetsControllerCompat(window, window.decorView).apply {
                isAppearanceLightNavigationBars = false
                window.navigationBarColor = Color.WHITE
            }
        }
    }

    private fun saveEmployees(sharedPreferences: SharedPreferences, employees: List<Employee>) {
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(employees)
        editor.putString("employee_list", json)
        editor.apply()
    }

    private fun loadEmployees(sharedPreferences: SharedPreferences): List<Employee> {
        val json = sharedPreferences.getString("employee_list", null) ?: return emptyList()
        val type = object : TypeToken<List<Employee>>() {}.type
        return Gson().fromJson(json, type)
    }
}