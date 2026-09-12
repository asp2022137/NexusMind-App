package com.example.projectapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// FR1, FR23 - User Consent Data Model
data class UserConsentState(
    val isFacialConsentGiven: Boolean = false,
    val isBehavioralConsentGiven: Boolean = false
)

@Composable
fun RegisterScreen(
    navController: NavController
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // FR2, FR3: Role Management (STUDENT or MENTOR)
    var selectedRole by remember { mutableStateOf("STUDENT") }

    // FR1, FR23: PDPA Privacy Consent State
    var consentState by remember { mutableStateOf(UserConsentState()) }

    // Consent දෙකම ලබා දී ඇත්නම් සහ Fields සම්පූර්ණ නම් පමණක් Form එක Valid වේ
    val isFormValid = name.isNotBlank() &&
            email.isNotBlank() &&
            password.isNotBlank() &&
            consentState.isFacialConsentGiven &&
            consentState.isBehavioralConsentGiven

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A90E2),
                        Color(0xFF6DD5FA),
                        Color(0xFF5CB85C)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()), // Scroll support for small screens
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Account",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4A90E2)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Join NexusMind AI Monitor",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Name Input Field
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Email Input Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Password Input Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(15.dp))

                // FR2, FR3: Role Selection (Student vs Mentor)
                Text(
                    text = "Select Account Role",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.Start)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedRole == "STUDENT",
                        onClick = { selectedRole = "STUDENT" }
                    )
                    Text(text = "Student", fontSize = 14.sp)

                    Spacer(modifier = Modifier.width(20.dp))

                    RadioButton(
                        selected = selectedRole == "MENTOR",
                        onClick = { selectedRole = "MENTOR" }
                    )
                    Text(text = "Mentor / Counselor", fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // FR1, FR23: PDPA Privacy Consent UI Component
                PrivacyConsentSection(
                    consentState = consentState,
                    onConsentChange = { updatedConsent -> consentState = updatedConsent }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Create Account Button
                Button(
                    onClick = {
                        if (isFormValid) {
                            // Selected role එක මත පදනම්ව Navigation තීරණය කිරීම
                            if (selectedRole == "STUDENT") {
                                navController.navigate("home") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                navController.navigate("mentor_dashboard") {
                                    popUpTo("register") { inclusive = true }
                                }
                            }
                        }
                    },
                    enabled = isFormValid, // Consent ලබාදෙන තෙක් Button එක Disabled වේ
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4A90E2),
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "CREATE ACCOUNT",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Back to Login Link
                TextButton(
                    onClick = {
                        navController.navigate("login") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                ) {
                    Text(
                        text = "Already have an account? Go to Login",
                        color = Color(0xFF5CB85C),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

// FR1, FR23 Component Implementation
@Composable
fun PrivacyConsentSection(
    consentState: UserConsentState,
    onConsentChange: (UserConsentState) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "Data Privacy & Consent (PDPA Compliance)",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4A90E2)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // FR1: Facial Emotion Consent Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = consentState.isFacialConsentGiven,
                onCheckedChange = { checked ->
                    onConsentChange(consentState.copy(isFacialConsentGiven = checked))
                }
            )
            Text(
                text = "Allow processing facial expression vectors on-device (No raw photos saved).",
                fontSize = 11.sp,
                color = Color.DarkGray
            )
        }

        // FR23: Behavioral & Sentiment Consent Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = consentState.isBehavioralConsentGiven,
                onCheckedChange = { checked ->
                    onConsentChange(consentState.copy(isBehavioralConsentGiven = checked))
                }
            )
            Text(
                text = "Consent to logging behavioral metrics and Sinhala/Singlish text sentiment.",
                fontSize = 11.sp,
                color = Color.DarkGray
            )
        }
    }
}