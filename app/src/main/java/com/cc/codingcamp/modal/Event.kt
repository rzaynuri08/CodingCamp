package com.cc.codingcamp.modal

data class Event(
    val id_event: String,
    val gambar: String,
    val judul_event: String,
    val keterangan: String,
    val pelaksanaan: String,
    val lokasi: String,
    val tanggal: String,
    val link_pendaftaran :String
)