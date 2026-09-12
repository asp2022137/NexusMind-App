package com.example.projectapplication.ui.screens

class ReportRepository {
    fun getRecommendations(): List<Recommendation> {
        return listOf(
            Recommendation("1", "Meditation", "Practice 10 mins daily", "Mental Wellness"),
            Recommendation("2", "Sleep Schedule", "Maintain 8 hours of sleep", "Physical Health")
        )
    }
}