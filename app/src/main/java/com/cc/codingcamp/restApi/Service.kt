package com.test.learnactivity.API

import com.cc.codingcamp.restApi.ApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    private const val BASE_URL = "https://billy30.000webhostapp.com/projectWebS3/api/" // Ganti dengan URL basis API

    // Properti apiService yang bisa diakses dari luar objek Service
    val apiService: ApiService by lazy {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)) // Perbaiki bagian ini
            .build()

        retrofit.create(ApiService::class.java) // Perbaiki bagian ini
    }
}
