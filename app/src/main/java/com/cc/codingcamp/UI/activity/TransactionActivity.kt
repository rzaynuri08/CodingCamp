package com.cc.codingcamp.UI.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.fragment.MetodepembayaranFragment
import com.cc.codingcamp.modal.Course
import com.cc.codingcamp.modal.ResponseModel
import com.cc.codingcamp.modal.Transaction
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransactionActivity : AppCompatActivity(), MetodepembayaranFragment.MetodePembayaranClickListener {

    private lateinit var switchDiscount: Switch
    private lateinit var txtDiskon: TextView
    private lateinit var txtTotalHargaProduk: TextView
    private lateinit var txtTotalPembayaran: TextView
    private lateinit var gambar: ImageView
    private lateinit var txtNamaProduk: TextView
    private lateinit var txtHarga: TextView
    private lateinit var txtIdProduk: TextView
    private lateinit var btnBayar: Button
    private lateinit var btnPembayaran: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction)

        switchDiscount = findViewById(R.id.switch1)
        txtDiskon = findViewById(R.id.txt_diskon)
        txtTotalHargaProduk = findViewById(R.id.txt_totalhargaproduk)
        txtTotalPembayaran = findViewById(R.id.txt_totalpembayaran)
        gambar = findViewById(R.id.gambarProduk)
        txtNamaProduk = findViewById(R.id.txt_namaproduk)
        txtHarga = findViewById(R.id.txt_hargaProduk)
        txtIdProduk = findViewById(R.id.idProduk)
        btnBayar = findViewById(R.id.btn_bayar)
        btnPembayaran = findViewById(R.id.btn_namapembayaran)

        val courseId = intent.getStringExtra("course_id")
        fetchData(courseId)

        // Default total pembayaran adalah harga produk
        var totalPembayaran = txtTotalHargaProduk.text.toString().toInt()

        // Mengatur listener pada Switch untuk mendeteksi perubahan status
        switchDiscount.setOnCheckedChangeListener { _, isChecked ->
            // Mengambil nilai diskon dari teks switchDiscount
            val disc = switchDiscount.text.toString()
            val diskon = switchDiscount.text.toString().toInt()

            val hargaProduk = txtTotalHargaProduk.text.toString().toInt()
            // Jika Switch aktif (ON), kurangkan diskon dari total pembayaran
            if (isChecked) {
                txtDiskon.text = disc
                totalPembayaran -= diskon
            } else {
                // Jika Switch non-aktif (OFF), tambahkan diskon kembali ke total pembayaran
                txtDiskon.text = "0"
                totalPembayaran = hargaProduk
            }
            // Tampilkan total pembayaran yang baru
            txtTotalPembayaran.text = totalPembayaran.toString()
        }

        btnBayar.setOnClickListener {
            val subtotal = txtTotalHargaProduk.text.toString()
            val koinDipakai = txtDiskon.text.toString()
            val total = txtTotalPembayaran.text.toString()

            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")

            val idModul = findViewById<TextView>(R.id.idProduk).text.toString()
            val idPembayaran = findViewById<TextView>(R.id.txt_metodepembayaran).text.toString()

            // Check if idPembayaran is null or empty
            if (idPembayaran.isNullOrEmpty()) {
                Toast.makeText(this@TransactionActivity, "Pilih metode pembayaran terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val transaksi = Transaction(subtotal, koinDipakai, total, username, idModul, idPembayaran)
                MakeTransaction(transaksi)
            }
        }

        btnPembayaran.setOnClickListener {
            val dialogFragment = MetodepembayaranFragment()
            dialogFragment.show(supportFragmentManager, "MetodePembayaranDialogFragment")
        }
    }

    private fun MakeTransaction(transaksi: Transaction) {
        val apiService = Service.apiService

        apiService.Transaction(transaksi).enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@TransactionActivity, "Transaction successful!", Toast.LENGTH_SHORT).show()
                    // Handle success, e.g., navigate to a different activity
                } else {
                    Toast.makeText(this@TransactionActivity, "Transaction failed. Please try again.", Toast.LENGTH_SHORT).show()
                    // Handle failure
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                Toast.makeText(this@TransactionActivity, "Transaction failed. Please try again.", Toast.LENGTH_SHORT).show()
                // Handle failure (e.g., network error)
            }
        })
    }

    private fun fetchData(courseId: String?) {
        val apiService = Service.apiService
        apiService.getCourseDetail(courseId).enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    val courseList = response.body()
                    if (courseList != null && courseList.isNotEmpty()) {
                        // Pilih item pertama untuk ditampilkan
                        val course = courseList[0]
                        Picasso.get().load(course.gambar).into(gambar)
                        txtNamaProduk.text = course.judul
                        txtHarga.text = course.harga.toString()
                        txtIdProduk.text = course.id_modul

                        val initialTotal = txtHarga.text.toString().toInt()
                        txtTotalHargaProduk.text = initialTotal.toString()
                        txtTotalPembayaran.text = initialTotal.toString()
                    } else {
                        // Tangani jika data kosong
                        // For example, show a message or handle accordingly
                    }
                } else {
                    // Tangani kesalahan respons dari API (contohnya, respons tidak berhasil)
                    // For example, show a message or handle accordingly
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
                // For example, show a message or handle accordingly
            }
        })
    }

    // Implementasi dari interface MetodePembayaranClickListener
    override fun onMetodePembayaranClicked(idPembayaran: String, namaPembayaran: String, icon: String) {
        // Gunakan data yang dipilih di sini
        // Misalnya, set data tersebut ke TextView atau ImageView
        findViewById<TextView>(R.id.txt_metodepembayaran).text = idPembayaran
        findViewById<Button>(R.id.btn_namapembayaran).text = namaPembayaran

        // Anda juga bisa menyimpan data ini ke dalam SharedPreferences atau variabel lainnya
        // sesuai kebutuhan aplikasi Anda
    }
}
