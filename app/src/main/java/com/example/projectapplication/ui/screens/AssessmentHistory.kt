package com.example.projectapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Assessment History Data Model (Updated with UI properties)
data class AssessmentHistory(
    val id: String = "",
    val date: String = "",
    val riskLevel: String = "",
    val score: Int = 0,
    val confidence: String = "",
    val riskColor: Color = Color(0xFF4CAF50)
)

// Internal UI Data Model
data class HistoryItem(
    val date: String,
    val confidence: String,
    val riskLevel: String,
    val riskColor: Color
)

@Composable
fun AssessmentHistoryScreen(
    navController: NavController
) {
    // Sample History Data matching the UI design
    val historyList = listOf(
        HistoryItem("2026-08-01", "88% Confidence", "Low Risk", Color(0xFF4CAF50)),
        HistoryItem("2026-07-25", "81% Confidence", "Medium Risk", Color(0xFFFF9800)),
        HistoryItem("2026-07-18", "92% Confidence", "High Risk", Color(0xFFE53935)),
        HistoryItem("2026-07-10", "85% Confidence", "Low Risk", Color(0xFF4CAF50)),
        HistoryItem("2026-06-28", "79% Confidence", "Medium Risk", Color(0xFFFF9800))
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1B759C), // Top Dark Teal/Blue
                        Color(0xFF00ACC1), // Middle Light Cyan
                        Color(0xFFE0F7FA)  // Bottom Soft Light Blue
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // ==========================================
            // Top Bar with Back Navigation Button
            // ==========================================
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 12.dp, start = 8.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Arrow Button
                IconButton(
                    onClick = { navController.popBackStack() }
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
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // ==========================================
            // History Cards List
            // ==========================================
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(historyList) { item ->
                    HistoryCardItem(item)
                }
            }
        }
    }
}

@Composable
fun HistoryCardItem(item: HistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.92f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Calendar Icon
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = Color(0xFF1B759C),
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = item.date,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = item.confidence,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            // Risk Level Badge
            Text(
                text = item.riskLevel,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = item.riskColor
            )
        }
    }
}