package com.example.projectapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SentimentInputSection(
    modifier: Modifier = Modifier,
    onSubmitSentiment: (String) -> Unit
) {
    var textInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val maxCharLimit = 5000

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Section Title
            Text(
                text = "3. Current Feeling & Sentiment",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Subtitle / Instruction (FR 8 - Multilingual support hint)
            Text(
                text = "Express how you feel in English, Sinhala, or Singlish.",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Text Field (FR 8, FR 9 - Multilingual & max 5000 characters limit)
            OutlinedTextField(
                value = textInput,
                onValueChange = { newText ->
                    if (newText.length <= maxCharLimit) {
                        textInput = newText
                        errorMessage = "" // Clear error when user types
                    } else {
                        errorMessage = "Maximum character limit ($maxCharLimit) reached."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                placeholder = {
                    Text(text = "How are you feeling today? / අද ඔයාට කොහොමද දැනෙන්නේ? / Ada oyata kohomada danenne?")
                },
                shape = RoundedCornerShape(8.dp),
                isError = errorMessage.isNotEmpty(),
                maxLines = 6
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Character Counter & Error Message Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }

                // Character Counter (e.g., 120 / 5000)
                Text(
                    text = "${textInput.length} / $maxCharLimit",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (textInput.length == maxCharLimit) Color.Red else Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button (FR 10 - Submit text for Sentiment Analysis)
            Button(
                onClick = {
                    val trimmedText = textInput.trim()
                    if (trimmedText.isEmpty()) {
                        errorMessage = "Please enter your thoughts before submitting."
                    } else {
                        errorMessage = ""
                        onSubmitSentiment(trimmedText)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Submit Sentiment Analysis",
                    fontSize = 16.sp
                )
            }
        }
    }
}
