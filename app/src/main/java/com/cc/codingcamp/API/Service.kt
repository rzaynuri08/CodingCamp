package com.test.learnactivity.API

import com.cc.codingcamp.API.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private const val BASE_URL = "https://apicodingcamp.000webhostapp.com/" // Ganti dengan URL basis API

    // Properti apiService yang bisa diakses dari luar objek Service
    val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
        }
}