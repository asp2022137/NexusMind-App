package com.example.projectapplication.data

import com.example.projectapplication.network.RetrofitClient

class PredictionRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun sendAssessmentData(
        sleepHours: Float,
        studyHours: Float,
        screenTimeHours: Float,
        facialScore: Float,
        textFeeling: String
    ): Result<MentalHealthAssessmentResponse> {
        return try {
            val requestPayload = MentalHealthAssessmentRequest(
                sleepHours = sleepHours,
                studyHours = studyHours,
                screenTimeHours = screenTimeHours,
                facialScore = facialScore,
                textFeeling = textFeeling
            )

            val response = apiService.predictMentalHealth(requestPayload)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Server Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}