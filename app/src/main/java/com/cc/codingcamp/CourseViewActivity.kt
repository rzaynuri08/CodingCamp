package com.cc.codingcamp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.adapter.CourseAdapter
import com.cc.codingcamp.modal.Course
import com.cc.codingcamp.restApi.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseViewActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_view)

        recyclerView = findViewById(R.id.recyclerViewCourse) // Pastikan ID RecyclerView di layout Anda sesuai
        recyclerView.layoutManager = LinearLayoutManager(this)
        val judulTextView = findViewById<TextView>(R.id.judul)

        val idJenisModul = intent.getStringExtra("id_jenismodul")
        val namaJenis = intent.getStringExtra(("nama_jenis"))

        if (idJenisModul != null) {
            // Jika ada, Anda dapat mengubah teks TextView sesuai dengan data tersebut
            judulTextView.text = "$namaJenis"
        } else {
            // Jika tidak ada id_jenismodul, tetapkan teks default
            judulTextView.text = "-"
        }

        // Inisialisasi adapter
        courseAdapter = CourseAdapter(this, emptyList()) // Kosongkan daftar kursus awal

        // Set adapter ke RecyclerView
        recyclerView.adapter = courseAdapter

        // Ambil data kursus dari API
        fetchCourseData(idJenisModul)
    }

    private fun fetchCourseData(idJenisModul: String?) {
        val apiService = Service.apiService
        apiService.getCourseView(idJenisModul).enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    val courseList = response.body()
                    if (courseList != null) {
                        // Perbarui data dalam adapter
                        courseAdapter.courseList = courseList
                        courseAdapter.notifyDataSetChanged()
                    } else {
                        // Tangani jika data null atau kosong
                        // Misalnya, tampilkan pesan bahwa tidak ada data.
                        // Anda juga dapat menampilkan pesan kosong di layout XML jika diperlukan.
                        // Misalnya, tambahkan TextView dengan pesan "Tidak ada data" di layout XML.
                    }
                } else {
                    // Handle kesalahan respons dari API (misalnya respons tidak sukses)
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                // Handle kesalahan koneksi atau kesalahan lainnya
                // Misalnya, tampilkan pesan kesalahan atau notifikasi ke pengguna.
            }
        })
    }
}
