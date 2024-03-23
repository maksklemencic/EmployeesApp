package com.example.employeesapp.ui.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.employeesapp.classes.Employee
import com.example.employeesapp.classes.Gender
import com.example.employeesapp.ui.dropdowns.GenderDropdown

@Composable
fun AddEmployeeDialog(
    showDialog: Boolean,
    onAddEmployee: (Employee) -> Unit,
    onCloseDialog: () -> Unit
) {
    var newEmployee by remember { mutableStateOf(Employee(0, "", "", 0, Gender.NotSpecified)) }
    var ageString by remember { mutableStateOf("") }

    val resetDialog = {
        newEmployee = Employee(0, "", "", 0, Gender.NotSpecified)
        ageString = ""
        onCloseDialog()
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                newEmployee = Employee(0, "", "", 0, Gender.NotSpecified)
            },
            title = { Text("Add New Employee") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newEmployee.name,
                        onValueChange = { newName -> newEmployee = newEmployee.copy(name = newName) },
                        label = { Text("Name") }
                    )
                    OutlinedTextField(
                        value = newEmployee.lastName,
                        onValueChange = { newLastName -> newEmployee = newEmployee.copy(lastName = newLastName) },
                        label = { Text("Lastname") }
                    )
                    OutlinedTextField(
                        value = ageString,
                        onValueChange = { newAge ->
                            ageString = newAge
                            newEmployee = newEmployee.copy(age = newAge.toIntOrNull() ?: 0)
                        },
                        label = { Text("Age") },
                        isError = ageString.isBlank() || ageString.toIntOrNull() ?: 0 <= 0
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Gender:", modifier = Modifier.padding(end = 8.dp))
                        GenderDropdown(
                            selectedGender = newEmployee.gender,
                            onGenderSelected = { newGender -> newEmployee = newEmployee.copy(gender = newGender) }
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onAddEmployee(newEmployee)
                        resetDialog()
                    },
                    enabled = newEmployee.name.isNotBlank() && newEmployee.lastName.isNotBlank() && newEmployee.age > 0
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                Button(onClick = { resetDialog() }) {
                    Text("Cancel")
                }
            },
        )
    }
}