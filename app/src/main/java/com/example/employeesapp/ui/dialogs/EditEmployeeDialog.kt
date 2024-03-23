package com.example.employeesapp.ui.dialogs

import androidx.compose.foundation.layout.*
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
fun EditEmployeeDialog(
    employee: Employee?,
    onDismissRequest: () -> Unit,
    onSaveEmployee: (Employee) -> Unit
) {
    var tempEmployee by remember { mutableStateOf(employee ?: Employee(0, "", "", 0, Gender.NotSpecified)) }
    var ageString by remember { mutableStateOf(tempEmployee?.age.toString()) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text("Edit Employee") },
        text = {
            Column {
                OutlinedTextField(
                    value = tempEmployee.name,
                    onValueChange = { newName -> tempEmployee = tempEmployee.copy(name = newName) },
                    label = { Text("Name") },
                    isError = tempEmployee.name.isBlank()
                )
                OutlinedTextField(
                    value = tempEmployee.lastName,
                    onValueChange = { newLastName -> tempEmployee = tempEmployee.copy(lastName = newLastName) },
                    label = { Text("Lastname") },
                    isError = tempEmployee.lastName.isBlank()
                )
                OutlinedTextField(
                    value = ageString,
                    onValueChange = {
                        ageString = it
                        tempEmployee = tempEmployee.copy(age = it.toIntOrNull() ?: 0)
                    },
                    label = { Text("Age") },
                    singleLine = true,
                    isError = ageString.isBlank() || ageString.toIntOrNull() ?: 0 <= 0
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ) {
                    Text("Gender:", modifier = Modifier.padding(end = 8.dp))
                    GenderDropdown(
                        selectedGender = tempEmployee.gender,
                        onGenderSelected = { newGender -> tempEmployee = tempEmployee.copy(gender = newGender) }
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (tempEmployee.name.isNotBlank() && tempEmployee.lastName.isNotBlank() && ageString.toIntOrNull() ?: 0 > 0) {
                        onSaveEmployee(tempEmployee)
                        onDismissRequest()
                    }
                },
                enabled = tempEmployee.name.isNotBlank() && tempEmployee.lastName.isNotBlank() && ageString.toIntOrNull() ?: 0 > 0
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text("Cancel")
            }
        }
    )
}
