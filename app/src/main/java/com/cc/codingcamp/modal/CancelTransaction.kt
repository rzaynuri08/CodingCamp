package com.cc.codingcamp.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CancelTransaction (
    @SerializedName("id_transaksi")
    @Expose
    val id_transaksi: String? = null,

    @SerializedName("username")
    @Expose
    val username: String? = null,

    @SerializedName("koin_dipakai")
    @Expose
    val koin_dipakai: String? = null
)