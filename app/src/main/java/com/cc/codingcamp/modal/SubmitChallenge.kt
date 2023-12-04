package com.cc.codingcamp.modal

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SubmitChallenge(
    @SerializedName("username")
    @Expose
    val username: String? = null,

    @SerializedName("id_challenge")
    @Expose
    val id_challenge: String? = null,

    @SerializedName("keterangan")
    @Expose
    val keterangan: String? = null,

    @SerializedName("linkpengumpulan")
    @Expose
    val linkpengumpulan: String? = null
)