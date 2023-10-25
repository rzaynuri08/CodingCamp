package com.cc.codingcamp.restApi

import com.cc.codingcamp.modal.Event
import com.cc.codingcamp.modal.Register
import com.cc.codingcamp.modal.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import com.cc.codingcamp.modal.User
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded

interface ApiService {
    // Definisikan endpoint untuk mengambil daftar pengguna
    @GET("data.php") // Ganti dengan URL sesuai dengan API Anda
    fun getUsers(): Call<List<User>>

    @GET("dataevent.php") // Ganti dengan URL sesuai dengan API Anda
    fun getEvent(): Call<List<Event>>

    @POST("register.php")
    fun registerUser(@Body user: Register): Call<ResponseModel>
}