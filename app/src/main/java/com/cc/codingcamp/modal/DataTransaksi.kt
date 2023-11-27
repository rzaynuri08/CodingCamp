package com.cc.codingcamp.modal

data class DataTransaksi (
    val id_transaksi: String,
    val subtotal: String,
    val koin_dipakai: String,
    val total: String,
    val jenis_status: String,
    val judul: String,
    val harga: String,
    val nama_pembayaran: String,
    val icon: String,
    val gambar: String,
    val tanggal_transaksi: String,
    val id_status: String
)