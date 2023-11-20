package com.cc.codingcamp.API

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Service {
    const val BASE_URL = "https://apicodingcamp.000webhostapp.com/api/" // Ganti dengan URL basis API

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