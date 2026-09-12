package com.example.projectapplication.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.projectapplication.data.PredictionRepository
import kotlinx.coroutines.launch

@Composable
fun PredictionScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val repository = remember { PredictionRepository() }

    // User Input States
    var sleepHours by remember { mutableStateOf("") }
    var studyHours by remember { mutableStateOf("") }
    var screenTime by remember { mutableStateOf("") }
    var moodText by remember { mutableStateOf("") }

    // Captured Facial Emotion State Holder
    var capturedEmotionResult by remember { mutableStateOf<FacialEmotionResult?>(null) }

    // Network Loading State
    var isLoading by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                    text = "AI Assessment & Prediction",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    // 1. Facial Emotion Analysis Section
                    Text(
                        text = "1. Facial Emotion Analysis",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Dynamic Camera Trigger Component
                    CaptureFaceEmotionSection(
                        onEmotionCaptured = { emotionResult ->
                            capturedEmotionResult = emotionResult
                            Log.d("PredictionScreen", "Detected Emotion: ${emotionResult.primaryEmotion}")
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 2. Behaviour Information Section
                    Text(
                        text = "2. Behaviour Information",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = sleepHours,
                        onValueChange = { sleepHours = it },
                        label = { Text("Sleep Hours (per day)") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Nightlight,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = studyHours,
                        onValueChange = { studyHours = it },
                        label = { Text("Study Hours (per day)") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = screenTime,
                        onValueChange = { screenTime = it },
                        label = { Text("Screen Time Hours (per day)") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Smartphone,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. Current Feeling & Sentiment Section
                    Text(
                        text = "3. Current Feeling & Sentiment",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = moodText,
                        onValueChange = { moodText = it },
                        label = { Text("How are you feeling today? (Describe below...)") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Mood,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    // Primary Action Button: API Call & Assessment Report
                    Button(
                        onClick = {
                            val sleep = sleepHours.toFloatOrNull() ?: 0f
                            val study = studyHours.toFloatOrNull() ?: 0f
                            val screen = screenTime.toFloatOrNull() ?: 0f

                            // 1. Captured Facial Emotion Score (FR 12-15)
                            val facialScore = capturedEmotionResult?.emotionScore ?: 0.5f

                            isLoading = true

                            coroutineScope.launch {
                                // 2. Fusion Data එකවර Backend එකට Send කිරීම
                                val result = repository.sendAssessmentData(
                                    sleepHours = sleep,
                                    studyHours = study,
                                    screenTimeHours = screen,
                                    facialScore = facialScore,
                                    textFeeling = moodText // 3. Text Sentiment Input
                                )

                                isLoading = false

                                result.onSuccess { response ->
                                    // Response එකෙන් ලැබුණු Stress/Anxiety/Burnout Output එක Report Screen එකට Pass කිරීම
                                    Toast.makeText(
                                        context,
                                        "Analysis Complete! Risk: ${response.riskCategory}",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    navController.navigate("report")
                                }.onFailure { error ->
                                    Toast.makeText(
                                        context,
                                        "API Failed: ${error.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        },
                        enabled = !isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isLoading) "Analyzing..." else "Analyze Mental Health",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Secondary Action Button: Direct to Recommendations
                    OutlinedButton(
                        onClick = { navController.navigate("recommendation") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Analyze Sentiment & Get Recommendations",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        // Overlay Progress Bar during API Request
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}