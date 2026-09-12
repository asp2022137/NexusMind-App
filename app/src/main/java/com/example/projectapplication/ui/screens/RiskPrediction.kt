package com.example.projectapplication.ui.screens

class RiskPrediction {
    fun predictRisk(score: Float): PredictionResult {
        return when {
            score < 0.4f -> PredictionResult("High Risk", score, "Needs immediate attention.")
            score < 0.7f -> PredictionResult("Medium Risk", score, "Moderate stress detected.")
            else -> PredictionResult("Low Risk", score, "Overall mental wellness is good.")
        }
    }
}