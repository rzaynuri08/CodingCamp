package com.cc.codingcamp.API

import com.cc.codingcamp.modal.CancelTransaction
import com.cc.codingcamp.modal.Challenge
import com.cc.codingcamp.modal.Course
import com.cc.codingcamp.modal.DataTransaksi
import com.cc.codingcamp.modal.Event
import com.cc.codingcamp.modal.JenisModul
import com.cc.codingcamp.modal.MainPreview
import com.cc.codingcamp.modal.Materi
import com.cc.codingcamp.modal.MetodePembayaran
import com.cc.codingcamp.modal.ModulDimiliki
import com.cc.codingcamp.modal.Payment
import com.cc.codingcamp.modal.Register
import com.cc.codingcamp.modal.ResponseModel
import com.cc.codingcamp.modal.SubPreview
import com.cc.codingcamp.modal.Transaction
import retrofit2.Call
import retrofit2.http.GET
import com.cc.codingcamp.modal.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
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

    @GET("datachallenge.php")
    fun getChallenge(): Call<List<Challenge>>

    @GET("datametodepembayaran.php")
    fun getMetodePembayaran() : Call<List<MetodePembayaran>>

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

    @GET("datamaterimodul.php")
    fun getMateri(@Query("id_subbab")idSubBab: String?) : Call<List<Materi>>

    @GET("detailtransaksi.php")
    fun getDataPayment(@Query("id_transaksi")idTransaksi: String?): Call<List<Payment>>

    @GET("datatransaksi.php")
    fun getDataDetailTransaksi(@Query("id_transaksi")transaksiId : String?): Call<List<DataTransaksi>>

    @GET("datatransaksi.php")
    fun getDataTransaksi(
        @Query("id_status") idStatus: String?,
        @Query("username") username: String?
    ): Call<List<DataTransaksi>>

    @POST("register.php")
    fun registerUser(@Body user: Register): Call<ResponseModel>

    @POST("transaksimodul.php")
    fun Transaction (@Body transaksi: Transaction): Call<ResponseModel>

    @POST("batalkantransaksi.php")
    fun cancelTransaction (@Body transaksi: CancelTransaction) : Call<ResponseModel>

    @Multipart
    @POST("uploadbukti.php") // Ganti dengan endpoint sesuai API Anda
    fun uploadImage(
        @Part bukti_pembayaran: MultipartBody.Part,
        @Part("id_transaksi") idTransaksi: RequestBody
    ): Call<ResponseBody>

}