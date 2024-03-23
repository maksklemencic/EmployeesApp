package com.example.employeesapp.ui.dropdowns

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.employeesapp.classes.Gender

@Composable
fun GenderDropdown(selectedGender: Gender, onGenderSelected: (Gender) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val genders = listOf(Gender.Male, Gender.Female, Gender.NotSpecified)

    val dropdownModifier = Modifier
        .clickable { expanded = true }
        .padding(8.dp) // Adjust padding to your needs
        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
        .padding(horizontal = 16.dp, vertical = 8.dp)

    Text(
        text = selectedGender.name,
        modifier = dropdownModifier,
        color = MaterialTheme.colorScheme.onPrimary,
        style = MaterialTheme.typography.bodyLarge
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth(0.6f)
    ) {
        genders.forEach { gender ->
            DropdownMenuItem(
                onClick = {
                    onGenderSelected(gender)
                    expanded = false
                },
                text = { Text(gender.name, Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
