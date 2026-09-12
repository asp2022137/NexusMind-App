package com.example.projectapplication.ui.screens

// FR16 - FR19: Text Sentiment Analysis Result Data Model
data class SentimentResult(
    val sentimentScore: Float,    // -1.0f (Negative/Stress) to +1.0f (Positive/Relaxed)
    val detectedLanguage: String, // SINGLISH, SINHALA, ENGLISH
    val riskFlag: Boolean         // True if high emotional distress detected
)

class TextSentimentAnalyzer {

    // Singlish & Sinhala High-Stress / Negative Keywords Dictionary (FR16, FR18)
    private val negativeSinglishKeywords = listOf(
        "stress", "amaru", "amaruui", "epawela", "epa wela", "sed", "sad", "bad",
        "baya", "bayawei", "dukayi", "dukai", "kenthayi", "kenthai", "ridenawa",
        "aul", "awul", "karadarayi", "asathutayi", "kamali", "fail", "fear"
    )

    // Positive Keywords Dictionary
    private val positiveSinglishKeywords = listOf(
        "hondayi", "hondai", "hondatama", "fatta", "happy", "sathutui", "sathutuyi",
        "good", "great", "passed", "subha", "harima lassanai", "relaxed", "fine"
    )

    /**
     * Legacy Function Compatibility
     */
    fun analyzeText(input: String): Float {
        return analyzeSentiment(input).sentimentScore
    }

    /**
     * FR16 - FR19: Singlish, Sinhala, and English Code-Mixed Sentiment Processing Engine
     */
    fun analyzeSentiment(input: String): SentimentResult {
        if (input.isBlank()) {
            return SentimentResult(
                sentimentScore = 0.0f,
                detectedLanguage = "UNKNOWN",
                riskFlag = false
            )
        }

        val cleanInput = input.lowercase().trim()

        var negativeMatchCount = 0
        var positiveMatchCount = 0

        // Check Negative Keywords (Singlish + Sinhala + English)
        for (keyword in negativeSinglishKeywords) {
            if (cleanInput.contains(keyword)) {
                negativeMatchCount++
            }
        }

        // Check Positive Keywords
        for (keyword in positiveSinglishKeywords) {
            if (cleanInput.contains(keyword)) {
                positiveMatchCount++
            }
        }

        // Calculate Sentiment Score between -1.0f and +1.0f
        var sentimentScore = 0.0f

        if (negativeMatchCount > 0 || positiveMatchCount > 0) {
            val netScore = positiveMatchCount - negativeMatchCount
            sentimentScore = (netScore.toFloat() / (positiveMatchCount + negativeMatchCount).coerceAtLeast(1)).coerceIn(-1.0f, 1.0f)
        } else {
            // Neutral text fallback
            sentimentScore = 0.2f
        }

        // Detect Language Type (Simple heuristic for Singlish detection)
        val detectedLang = if (cleanInput.contains("mat") || cleanInput.contains("wela") || cleanInput.contains("nn")) {
            "SINGLISH"
        } else {
            "ENGLISH/MIXED"
        }

        // Trigger High-Risk Flag if multiple negative emotional indicators are present
        val isHighRisk = negativeMatchCount >= 2 || sentimentScore <= -0.6f

        return SentimentResult(
            sentimentScore = sentimentScore,
            detectedLanguage = detectedLang,
            riskFlag = isHighRisk
        )
    }
}