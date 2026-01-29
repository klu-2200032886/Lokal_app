package com.example.otpauthapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SessionScreen(onLogout: () -> Unit) {
    var seconds by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            seconds++
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Session Active")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Duration: ${seconds / 60}:${seconds % 60}")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}
