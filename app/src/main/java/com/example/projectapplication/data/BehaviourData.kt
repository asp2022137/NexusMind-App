package com.example.projectapplication.data

import com.google.gson.annotations.SerializedName

// ==========================================
// 1. App එක ඇතුළේ (Local Logic) සඳහා Models
// ==========================================

// Input Data Structure (FR 11, FR 12)
data class BehaviourData(
    val sleepHours: Float,
    val studyHours: Float,
    val screenTimeHours: Float,
    val facialScore: Float = 0f,        // Range: 0.0 to 1.0 (FR 7)
    val textSentimentScore: Float = 0f  // Range: -1.0 to 1.0 (FR 10)
)

// Prediction & Local Analysis Results Output (FR 14, FR 16, FR 17, FR 18, FR 19)
data class AnalysisResult(
    val isDataValid: Boolean,
    val validationMessage: String = "",
    val overallRiskScore: Float = 0f,      // 0 to 100 (FR 16)
    val riskCategory: String = "Low",      // Low, Medium, High (FR 16)
    val stressScore: Float = 0f,           // 0 to 100 (FR 17)
    val stressLevel: String = "Low",       // Low, Moderate, High, Severe (FR 17)
    val anxietyCategory: String = "Low",   // Low, Medium, High (FR 18)
    val anxietyConfidence: Float = 0f,     // Percentage (FR 18)
    val burnoutProbability: Float = 0f,    // 0 to 100 % (FR 19)
    val isAtBurnoutRisk: Boolean = false,  // > 60% (FR 19)
    val anomalyDetected: Boolean = false   // (FR 14)
)

// ==========================================
// 2. Backend API (FastAPI / Flask) සඳහා Network Models
// ==========================================

// Backend Server එකට යවන Request Payload (FR 15)
data class MentalHealthAssessmentRequest(
    @SerializedName("sleep_hours") val sleepHours: Float,
    @SerializedName("study_hours") val studyHours: Float,
    @SerializedName("screen_time_hours") val screenTimeHours: Float,
    @SerializedName("facial_emotion_score") val facialScore: Float,
    @SerializedName("text_feeling") val textFeeling: String
)

// Backend Server එකෙන් ආපසු ලැබෙන Prediction Response (FR 16, 17, 18, 19)
data class MentalHealthAssessmentResponse(
    @SerializedName("status") val status: String,
    @SerializedName("stress_score") val stressScore: Float,
    @SerializedName("stress_level") val stressLevel: String,
    @SerializedName("anxiety_risk") val anxietyRisk: String,
    @SerializedName("burnout_probability") val burnoutProbability: Float,
    @SerializedName("overall_risk_score") val overallRiskScore: Float,
    @SerializedName("risk_category") val riskCategory: String,
    @SerializedName("recommendation_summary") val recommendationSummary: String? = null
)