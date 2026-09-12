package com.example.projectapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Data Model
data class LocalAssessmentHistory(
    val date: String,
    val stress: String,
    val anxiety: String,
    val burnout: String
)

@Composable
fun HistoryScreen(
    navController: NavController
) {
    val scrollState = rememberScrollState()

    // Temporary assessment history data
    val assessments = listOf(
        LocalAssessmentHistory(
            date = "01/08/2026",
            stress = "40%",
            anxiety = "Low",
            burnout = "20%"
        ),
        LocalAssessmentHistory(
            date = "15/08/2026",
            stress = "65%",
            anxiety = "Medium",
            burnout = "45%"
        ),
        LocalAssessmentHistory(
            date = "30/08/2026",
            stress = "75%",
            anxiety = "High",
            burnout = "60%"
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A90E2),
                        Color(0xFF6DD5FA),
                        Color(0xFFF5FAFF)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ==========================================
            // 1. Top Bar with Working Back Arrow
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        val popped = navController.popBackStack()
                        if (!popped) {
                            navController.navigate("prediction") {
                                launchSingleTop = true
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Assessment History",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // ==========================================
            // 2. Main History Card Container
            // ==========================================
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = "Previous Mental Health Reports",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A90E2)
                    )

                    Spacer(modifier = Modifier.height(15.dp))

                    assessments.forEach { item ->
                        HistoryCard(assessment = item)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ==========================================
            // 3. Back To Dashboard Button
            // ==========================================
            Button(
                onClick = {
                    val popped = navController.popBackStack()
                    if (!popped) {
                        navController.navigate("prediction") {
                            launchSingleTop = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A90E2)
                )
            ) {
                Text(
                    text = "Back To Dashboard",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun HistoryCard(
    assessment: LocalAssessmentHistory
) {
    val riskColor = when (assessment.anxiety) {
        "Low" -> Color(0xFF5CB85C)
        "Medium" -> Color(0xFFFF9800)
        "High" -> Color(0xFFE53935)
        else -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5FAFF)
        )
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                text = "📅 ${assessment.date}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Stress Level: ${assessment.stress}",
                fontSize = 14.sp
            )

            Text(
                text = "Anxiety Risk: ${assessment.anxiety}",
                fontSize = 14.sp,
                color = riskColor,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Burnout Probability: ${assessment.burnout}",
                fontSize = 14.sp
            )
        }
    }
}