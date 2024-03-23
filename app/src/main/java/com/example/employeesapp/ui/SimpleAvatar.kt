package com.example.employeesapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.math.BigInteger
import java.security.MessageDigest

@Composable
fun SimpleAvatar(
    name: String,
    size: Int = 40
) {
    val md = MessageDigest.getInstance("MD5")
    val nameHash = BigInteger(1, md.digest(name.toByteArray()))
    val colorInt = nameHash.toInt() or 0xFF000000.toInt()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(size.dp)
            .background(Color(colorInt))
    ) {
        val initials = name.split(" ").mapNotNull { it.firstOrNull()?.toString() }.take(2).joinToString("")
        Text(text = initials, color = Color.White)
    }
}
