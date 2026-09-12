package com.example.projectapplication.ui.screens

import com.example.projectapplication.network.ApiUser
import com.example.projectapplication.network.RetrofitClient

class ApiRepository {
    private val api = RetrofitClient.instance

    suspend fun loginUser(email: String, pass: String): ApiUser {
        return api.login(mapOf("email" to email, "password" to pass))
    }
}