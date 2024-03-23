package com.example.employeesapp.ui.screens

import android.content.SharedPreferences
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.employeesapp.classes.Employee
import com.example.employeesapp.classes.Gender
import com.example.employeesapp.ui.SimpleAvatar
import com.example.employeesapp.ui.dialogs.AddEmployeeDialog
import com.example.employeesapp.ui.dialogs.EditEmployeeDialog
import com.example.employeesapp.ui.dialogs.StatisticsDialog
import com.example.employeesapp.ui.theme.EmployeesAppTheme

@Composable
fun EmployeeListScreen(
    employees: MutableList<Employee>,
    onDeleteEmployee: (Employee) -> Unit,
    saveEmployees: (SharedPreferences, List<Employee>) -> Unit,
    sharedPreferences: SharedPreferences
) {

    var showAddEmployeeDialog by remember { mutableStateOf(false) }
    AddEmployeeDialog(
        showDialog = showAddEmployeeDialog,
        onAddEmployee = { newEmployee ->
            val updatedEmployee = newEmployee.copy(id = (employees.maxByOrNull { it.id }?.id ?: 0) + 1)
            employees.add(updatedEmployee)
            saveEmployees(sharedPreferences, employees)
            showAddEmployeeDialog = false
        },
        onCloseDialog = {
            showAddEmployeeDialog = false
        }
    )

    var showStatisticsDialog by remember { mutableStateOf(false) }
    if (showStatisticsDialog) {
        StatisticsDialog(
            employees = employees,
            onConfirm = { showStatisticsDialog = false }
        )
    }


    var showEditDialog by remember { mutableStateOf(false) }
    var editingEmployee by remember { mutableStateOf<Employee?>(null) }

    if (showEditDialog && editingEmployee != null) {
        EditEmployeeDialog(
            employee = editingEmployee,
            onDismissRequest = {
                showEditDialog = false
                editingEmployee = null
            },
            onSaveEmployee = { updatedEmployee ->
                employees[employees.indexOfFirst { it.id == updatedEmployee.id }] = updatedEmployee
                saveEmployees(sharedPreferences, employees)
                showEditDialog = false
                editingEmployee = null
            }
        )
    }

    EmployeesAppTheme {
        Column {
            Text(
                text = "Employee App",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp)
            )
            Divider(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier.padding(16.dp)
            ) {
                Button(
                    onClick = { showAddEmployeeDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                ) {
                    Text("Add")
                }
                Button(
                    onClick = { showStatisticsDialog = true },
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("Statistics")
                }
            }
            LazyColumn {
                items(items = employees, key = { employee ->
                    employee.id
                }) { employee ->
                    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                SimpleAvatar(name = "${employee.name} ${employee.lastName}", size = 40)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "${employee.name} ${employee.lastName}",
                                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                            Row {
                                IconButton(
                                    onClick = {
                                        showEditDialog = true
                                        editingEmployee = employee
                                    }
                                ) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = MaterialTheme.colorScheme.primary)
                                }
                                IconButton(
                                    onClick = { onDeleteEmployee(employee) }
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.padding(start = 0.dp, top = 4.dp, bottom = 12.dp), // Adjust the start padding to align with avatar, and adjust the vertical padding
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = "Info",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = buildString {
                                    if (employee.gender == Gender.Male || employee.gender == Gender.Female) {
                                        append("Gender: ${employee.gender}, ")
                                    }
                                    append("Age: ${employee.age}")
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Divider()
                    }
                }
            }
        }
    }
}
