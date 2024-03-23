package com.example.employeesapp.ui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.employeesapp.classes.Employee
import com.example.employeesapp.classes.Gender

@Composable
fun StatisticsDialog(
    employees: List<Employee>,
    onConfirm: () -> Unit
) {
    val averageAge = employees.map { it.age }.average().let { "%.1f".format(it) }
    var employeesCount = employees.count { it.gender != Gender.NotSpecified }
    val maleCount = employees.count { it.gender == Gender.Male }.toDouble()
    val femaleCount = employees.count { it.gender == Gender.Female }.toDouble()
    val maleRatio = (maleCount / employeesCount)
    val femaleRatio = (femaleCount / employeesCount)

    val maleToFemaleRatio = if (femaleCount > 0) {
        maleRatio / femaleRatio
    } else {
        Double.POSITIVE_INFINITY
    }

    val ratioString = if (femaleCount > 0) {
        "%.1f".format(maleToFemaleRatio)
    } else {
        "100"
    }

    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text("Statistics") },
        text = {
            Column {
                Row {
                    Text(
                        text = "Average age: ",
                    )
                    Text(
                        text = "$averageAge years",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
                Row {
                    Text(
                        text = "Male/Female ratio: ",
                    )
                    Text(
                        text = "$ratioString",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("OK")
            }
        }
    )
}
