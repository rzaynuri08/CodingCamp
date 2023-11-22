package com.cc.codingcamp.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Transaction(
    @SerializedName("subtotal")
    @Expose
    val subtotal: String? = null,

    @SerializedName("koin_dipakai")
    @Expose
    val koin_dipakai: String? = null,

    @SerializedName("total")
    @Expose
    val total: String? = null,

    @SerializedName("username")
    @Expose
    val username: String? = null,

    @SerializedName("id_modul")
    @Expose
    val id_modul: String? = null,

    @SerializedName("id_pembayaran")
    @Expose
    val id_pembayaran: String? = null

)