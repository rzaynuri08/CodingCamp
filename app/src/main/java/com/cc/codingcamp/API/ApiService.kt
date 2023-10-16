package com.cc.codingcamp.API

import com.cc.codingcamp.modal.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import com.cc.codingcamp.modal.User
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {
    // Definisikan endpoint untuk mengambil daftar pengguna
    @GET("data.php") // Ganti dengan URL sesuai dengan API Anda
    fun getUsers(): Call<List<User>>

    @POST("register.php") // Ganti dengan endpoint yang sesuai
    fun registerUser(@Body user: User): Call<ResponseModel>
}