package com.cc.codingcamp.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Register(
    @SerializedName("username")
    @Expose
    val username: String? = null,

    @SerializedName("nama_lengkap")
    @Expose
    val nama_lengkap: String? = null,

    @SerializedName("password")
    @Expose
    val password: String? = null,

    @SerializedName("no_hp")
    @Expose
    val no_hp: String? = null,

    @SerializedName("email")
    @Expose
    val email: String? = null

)