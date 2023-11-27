package com.cc.codingcamp.UI.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.CancelTransaction
import com.cc.codingcamp.modal.DataTransaksi
import com.cc.codingcamp.modal.ResponseModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UnpaidActivity : AppCompatActivity() {
    private lateinit var txtStatus: TextView
    private lateinit var txtNamaProduk: TextView
    private lateinit var txtHargaProduk: TextView
    private lateinit var txtNamaPembayaran: TextView
    private lateinit var txtIdTransaksi: TextView
    private lateinit var txtTotalHargaProduk: TextView
    private lateinit var txtDiskon: TextView
    private lateinit var txtTotal: TextView
    private lateinit var gambarProdk: ImageView
    private lateinit var btnSelesaikanPesanan: Button
    private lateinit var btnBatalkanPesanan: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unpaid)

        txtStatus = findViewById(R.id.txt_statusdetailtransaksi)
        txtNamaProduk = findViewById(R.id.txt_namaproduktransaksi)
        txtHargaProduk = findViewById(R.id.txt_hargaProduktransaksi)
        txtNamaPembayaran = findViewById(R.id.txt_namapembayaran)
        txtIdTransaksi = findViewById(R.id.txt_idtransaksi)
        txtTotalHargaProduk = findViewById(R.id.txt_totalhargaproduk)
        txtDiskon = findViewById(R.id.txt_diskon)
        txtTotal = findViewById(R.id.txt_totalpembayaran)
        gambarProdk = findViewById(R.id.gambarProduk)
        btnSelesaikanPesanan = findViewById(R.id.btn_selesaikanpesanan)
        btnBatalkanPesanan = findViewById(R.id.btn_batalkanpesanan)

        btnSelesaikanPesanan.setOnClickListener{
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra("idTransaksi", txtIdTransaksi.text.toString())
            startActivity(intent)
        }

        btnBatalkanPesanan.setOnClickListener{
            showConfirmationDialog()
        }

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
                    Toast.makeText(this@UnpaidActivity, "Gagal memuat data detail transaksi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<DataTransaksi>>, t: Throwable) {
                Toast.makeText(this@UnpaidActivity, "Gagal memuat data detail transaksi", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Konfirmasi")
        builder.setMessage("Apakah Anda yakin ingin membatalkan pesanan ini?")

        builder.setPositiveButton("Ya") { dialog, which ->
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")

            val transaksi = CancelTransaction(id_transaksi = "${txtIdTransaksi.text}", username = username, koin_dipakai = "${txtDiskon.text}")
            cancelTransaction(transaksi)
            dialog.dismiss()
        }

        builder.setNegativeButton("Tidak") { dialog, which ->
            // Tindakan yang diambil jika pengguna memilih "Tidak"
            // Misalnya, lakukan sesuatu di sini
            dialog.dismiss() // Tutup dialog setelah tindakan selesai
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun cancelTransaction(transaksi: CancelTransaction) {
        val apiService = Service.apiService
        val call = apiService.cancelTransaction(transaksi)

        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    Toast.makeText(this@UnpaidActivity, "berhasil", Toast.LENGTH_SHORT).show()
                    // Tangani respons yang berhasil di sini
                } else {
                    // Tangani respons gagal di sini
                    Toast.makeText(this@UnpaidActivity, "gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                // Tangani kegagalan koneksi atau respons di sini
            }
        })
    }

}