package com.example.projectapplication.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Local Machine එකේ FastAPI / Flask run වෙනවා නම් Android Emulator සඳහා 10.0.2.2 භාවිතා කරයි
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    // කලින් Code වල `RetrofitClient.instance` ලෙස call කර තිබුණේ නම් එම compatibility එක සඳහා:
    val instance: ApiService
        get() = apiService
}