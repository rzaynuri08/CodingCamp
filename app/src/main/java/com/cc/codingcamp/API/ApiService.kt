package com.cc.codingcamp.API

import com.cc.codingcamp.modal.Course
import com.cc.codingcamp.modal.Event
import com.cc.codingcamp.modal.JenisModul
import com.cc.codingcamp.modal.MainPreview
import com.cc.codingcamp.modal.ModulDimiliki
import com.cc.codingcamp.modal.Register
import com.cc.codingcamp.modal.ResponseModel
import com.cc.codingcamp.modal.SubPreview
import retrofit2.Call
import retrofit2.http.GET
import com.cc.codingcamp.modal.User
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query

interface ApiService {
    // Definisikan endpoint untuk mengambil daftar pengguna
    @GET("dataakun.php") // Ganti dengan URL sesuai dengan API Anda
    fun getUsers(): Call<List<User>>

    @GET("dataakun.php?username=")
    fun getSession(@Query("username") Userlog : String?): Call<List<User>>

    @GET("dataevent.php") // Ganti dengan URL sesuai dengan API Anda
    fun getEvent(): Call<List<Event>>

    @GET("dataevent.php?id_event=")
    fun getEventDetail(@Query("id_event") idEvent: String?) : Call<List<Event>>

    @GET("datajenismodul.php")
    fun getJenisModul(): Call<List<JenisModul>>

    @GET("datamodul.php")
    fun Getproduct() : Call<List<Course>>

    @GET("datamodul.php?id_jenismodul=")
    fun getCourseView(@Query("id_jenismodul") idJenisModul: String?) : Call<List<Course>>

    @GET("datamodul.php?id_modul=")
    fun getCourseDetail(@Query("id_modul") idModul : String?) :Call<List<Course>>

    @GET("datababmodul.php?id_modul=")
    fun getParentPreview(@Query("id_modul") idModul: String?) : Call<List<MainPreview>>

    @GET("datasubbabmodul.php?id_bab=")
    fun getSubPreview(@Query("id_bab") idBab: String?) : Call<List<SubPreview>>

    @GET("datamoduldimiliki.php")
    fun getModulDimiliki(@Query("username")Userlog: String?) : Call<List<ModulDimiliki>>

    @POST("register.php")
    fun registerUser(@Body user: Register): Call<ResponseModel>
}