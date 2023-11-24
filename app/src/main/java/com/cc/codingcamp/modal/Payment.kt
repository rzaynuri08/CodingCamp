package com.cc.codingcamp.modal

data class Payment (
    val id_transaksi: String,
    val nama_pembayaran: String,
    val no_rekening: String,
    val icon: String,
    val total: String
)