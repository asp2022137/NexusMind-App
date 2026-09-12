package com.example.projectapplication.ui.screens

data class PredictionResult(
    val riskLevel: String = "",
    val confidence: Float = 0f,
    val summary: String = ""
)