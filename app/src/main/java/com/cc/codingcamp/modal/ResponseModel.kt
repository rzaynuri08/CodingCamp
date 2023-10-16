package com.test.learnactivity.modal

data class ResponseModel(
    val success: Boolean, // Menyatakan apakah operasi berhasil atau tidak
    val message: String // Pesan dari server, misalnya "Pendaftaran berhasil" atau "Pendaftaran gagal"
)