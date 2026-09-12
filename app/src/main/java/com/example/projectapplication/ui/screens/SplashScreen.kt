package com.example.projectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "AI Mental Health App",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // Login එකට යද්දී Splash screen එක Memory එකෙන් (Backstack) ඉවත් කරයි
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            ) {
                Text("Get Started")
            }
        }
    }
}