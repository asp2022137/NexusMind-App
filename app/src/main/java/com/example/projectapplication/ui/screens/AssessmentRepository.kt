package com.example.projectapplication.ui.screens

class AssessmentRepository {
    fun getAssessmentHistory(): List<AssessmentHistory> {
        return listOf(
            AssessmentHistory("1", "2026-03-01", "Low Risk", 85),
            AssessmentHistory("2", "2026-03-15", "Medium Risk", 62)
        )
    }
}
