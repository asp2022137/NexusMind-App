package com.example.projectapplication.network

import com.example.projectapplication.data.MentalHealthAssessmentRequest
import com.example.projectapplication.data.MentalHealthAssessmentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    // 1. User Login Endpoint
    @POST("auth/login")
    suspend fun login(@Body request: Map<String, String>): ApiUser

    // 2. User Assessment History Endpoint
    @GET("user/history")
    suspend fun getHistory(): List<ApiAssessmentHistory>

    // 3. User Recommendations Endpoint
    @GET("user/recommendations")
    suspend fun getRecommendations(): List<ApiRecommendation>

    // 4. ML Prediction Endpoint (FR 15, 16, 17, 18, 19)
    @POST("predict/mental-health")
    suspend fun predictMentalHealth(
        @Body request: MentalHealthAssessmentRequest
    ): Response<MentalHealthAssessmentResponse>
}

// ==========================================
// Placeholder Data Models for Auth/History
// (If not defined in separate files)
// ==========================================
data class ApiUser(
    val id: String,
    val email: String,
    val token: String? = null
)

data class ApiAssessmentHistory(
    val id: String,
    val date: String,
    val riskCategory: String,
    val stressScore: Float
)

data class ApiRecommendation(
    val id: String,
    val title: String,
    val description: String,
    val category: String
)
