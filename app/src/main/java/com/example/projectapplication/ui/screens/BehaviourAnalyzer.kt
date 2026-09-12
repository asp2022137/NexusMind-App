// This file was a duplicate of AnalysisResult in BehaviourData.kt and has been emptied to resolve a redeclaration error.
package com.example.projectapplication.ui.screens

import com.example.projectapplication.data.AnalysisResult
import com.example.projectapplication.data.BehaviourData

class BehaviourAnalyzer {

    // FR 11: Behavioral Data Input Validation
    fun validateInput(data: BehaviourData): Pair<Boolean, String> {
        if (data.sleepHours < 0f || data.sleepHours > 24f) {
            return Pair(false, "Sleep hours must be between 0 and 24.")
        }
        if (data.studyHours < 0f || data.studyHours > 18f) {
            return Pair(false, "Study hours must be between 0 and 18.")
        }
        if (data.screenTimeHours < 0f || data.screenTimeHours > 24f) {
            return Pair(false, "Screen time must be between 0 and 24.")
        }
        if ((data.sleepHours + data.studyHours) > 24f) {
            return Pair(false, "Total sleep and study hours cannot exceed 24 hours.")
        }
        return Pair(true, "Validation Successful")
    }

    // FR 13 & 14: Behavioral Anomaly Detection
    private fun detectAnomalies(data: BehaviourData): Boolean {
        val sleepAnomaly = data.sleepHours < 4.0f || data.sleepHours > 11.0f
        val studyAnomaly = data.studyHours > 12.0f
        val screenAnomaly = data.screenTimeHours > 10.0f
        return sleepAnomaly || studyAnomaly || screenAnomaly
    }

    // FR 15: Multimodal Feature Vector Normalization (0.0 to 1.0)
    private fun createFeatureVector(data: BehaviourData): FloatArray {
        val normSleep = (data.sleepHours / 24f).coerceIn(0f, 1f)
        val normStudy = (data.studyHours / 18f).coerceIn(0f, 1f)
        val normScreen = (data.screenTimeHours / 24f).coerceIn(0f, 1f)

        // Convert Text Sentiment Score (-1.0 to +1.0) to (0.0 to 1.0) range
        val normSentiment = ((data.textSentimentScore + 1f) / 2f).coerceIn(0f, 1f)
        val normFacial = data.facialScore.coerceIn(0f, 1f)

        return floatArrayOf(normSleep, normStudy, normScreen, normSentiment, normFacial)
    }

    // FR 16, 17, 18, 19: Multimodal Fusion & Risk Prediction Logic
    fun processData(data: BehaviourData): AnalysisResult {
        // 1. Input Validation Check (FR 11)
        val (isValid, message) = validateInput(data)
        if (!isValid) {
            return AnalysisResult(
                isDataValid = false,
                validationMessage = message
            )
        }

        // 2. Feature Vector Extraction (FR 15)
        val features = createFeatureVector(data)
        val normSleep = features[0]
        val normStudy = features[1]
        val normScreen = features[2]
        val normSentiment = features[3] // Lower value indicates negative sentiment
        val normFacial = features[4]    // Higher value indicates stress/negative emotion

        // 3. Stress Level Estimation (FR 17)
        val sleepDeprivation = (1f - normSleep)
        val stressRaw = (sleepDeprivation * 30f) + (normScreen * 20f) + ((1f - normSentiment) * 25f) + (normFacial * 25f)
        val stressScore = stressRaw.coerceIn(0f, 100f)
        val stressLevel = when {
            stressScore < 30f -> "Low"
            stressScore < 60f -> "Moderate"
            stressScore < 85f -> "High"
            else -> "Severe"
        }

        // 4. Anxiety Risk Classification (FR 18)
        val anxietyScore = (sleepDeprivation * 35f) + (normStudy * 25f) + ((1f - normSentiment) * 40f)
        val anxietyCategory = when {
            anxietyScore < 35f -> "Low"
            anxietyScore < 70f -> "Medium"
            else -> "High"
        }
        val anxietyConfidence = anxietyScore.coerceIn(50f, 98f)

        // 5. Burnout Probability Calculation (FR 19)
        val burnoutProb = ((normStudy * 40f) + (sleepDeprivation * 40f) + (normScreen * 20f)).coerceIn(0f, 100f)
        val isAtBurnoutRisk = burnoutProb > 60f

        // 6. Overall Mental Health Risk Score (FR 16)
        val overallRiskScore = ((stressScore * 0.4f) + (anxietyScore * 0.4f) + (burnoutProb * 0.2f)).coerceIn(0f, 100f)
        val riskCategory = when {
            overallRiskScore < 35f -> "Low"
            overallRiskScore < 70f -> "Medium"
            else -> "High"
        }

        // Return Final Consolidated Analysis Result
        return AnalysisResult(
            isDataValid = true,
            validationMessage = "Success",
            overallRiskScore = overallRiskScore,
            riskCategory = riskCategory,
            stressScore = stressScore,
            stressLevel = stressLevel,
            anxietyCategory = anxietyCategory,
            anxietyConfidence = anxietyConfidence,
            burnoutProbability = burnoutProb,
            isAtBurnoutRisk = isAtBurnoutRisk,
            anomalyDetected = detectAnomalies(data)
        )
    }
}