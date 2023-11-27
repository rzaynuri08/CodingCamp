package com.cc.codingcamp.UI.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.DataTransaksi
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SucccessActivity : AppCompatActivity() {
    private lateinit var txtStatus: TextView
    private lateinit var txtNamaProduk: TextView
    private lateinit var txtHargaProduk: TextView
    private lateinit var txtNamaPembayaran: TextView
    private lateinit var txtIdTransaksi: TextView
    private lateinit var txtTotalHargaProduk: TextView
    private lateinit var txtDiskon: TextView
    private lateinit var txtTotal: TextView
    private lateinit var gambarProdk: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_succcess)

        txtStatus = findViewById(R.id.txt_statusdetailtransaksi)
        txtNamaProduk = findViewById(R.id.txt_namaproduktransaksi)
        txtHargaProduk = findViewById(R.id.txt_hargaProduktransaksi)
        txtNamaPembayaran = findViewById(R.id.txt_namapembayaran)
        txtIdTransaksi = findViewById(R.id.txt_idtransaksi)
        txtTotalHargaProduk = findViewById(R.id.txt_totalhargaproduk)
        txtDiskon = findViewById(R.id.txt_diskon)
        txtTotal = findViewById(R.id.txt_totalpembayaran)
        gambarProdk = findViewById(R.id.gambarProduk)

        val transaksiId = intent.getStringExtra("transaksi_id")

        val apiService = Service.apiService
        val call = apiService.getDataDetailTransaksi(transaksiId)

        call.enqueue(object : Callback<List<DataTransaksi>> {
            override fun onResponse(call: Call<List<DataTransaksi>>, response: Response<List<DataTransaksi>>) {
                if (response.isSuccessful) {
                    val dataTransaksiList = response.body()
                    if (dataTransaksiList != null && dataTransaksiList.isNotEmpty()) {
                        val dataTransaksi = dataTransaksiList[0]
                        txtStatus.text  = dataTransaksi.jenis_status
                        txtNamaProduk.text = dataTransaksi.judul
                        txtHargaProduk.text = "Rp. ${dataTransaksi.harga}"
                        txtNamaPembayaran.text = dataTransaksi.nama_pembayaran
                        txtIdTransaksi.text = dataTransaksi.id_transaksi
                        txtTotalHargaProduk.text = dataTransaksi.subtotal
                        txtDiskon.text = dataTransaksi.koin_dipakai
                        txtTotal.text = dataTransaksi.total
                        Picasso.get()
                            .load(dataTransaksi.gambar)
                            .into(gambarProdk)
                    } else {

                    }
                } else {
                    Toast.makeText(this@SucccessActivity, "Gagal memuat data detail transaksi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<DataTransaksi>>, t: Throwable) {
                Toast.makeText(this@SucccessActivity, "Gagal memuat data detail transaksi", Toast.LENGTH_SHORT).show()
            }
        })
    }
}