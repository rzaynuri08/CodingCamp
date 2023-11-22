package com.cc.codingcamp.UI.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.fragment.DetailproductFragment
import com.cc.codingcamp.UI.fragment.ReviewproductFragment
import com.cc.codingcamp.modal.Course
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursedetailActivity : AppCompatActivity() {
    private lateinit var gambar: ImageView
    private var isDetailFragmentActive = true
    private lateinit var btnBeliCourseDetail: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coursedetail)

        gambar = findViewById(R.id.img_gambarcoursedetail)

        val courseId = intent.getStringExtra("course_id")

        btnBeliCourseDetail = findViewById(R.id.btn_belicoursedetail)
        btnBeliCourseDetail.setOnClickListener {
            // Add the code to navigate to TransactionActivity
            val intent = Intent(this, TransactionActivity::class.java)
            intent.putExtra("course_id", courseId)
            startActivity(intent)
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.detail -> {
                    if (!isDetailFragmentActive) {
                        val bundle = Bundle()
                        bundle.putString("idJenisModul", courseId)
                        val detailProductFragment = DetailproductFragment()
                        detailProductFragment.arguments = bundle

                        replaceFragment(detailProductFragment)
                        isDetailFragmentActive = true
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.lainnya -> {
                    if (isDetailFragmentActive) {
                        val ReviewproductFragment = ReviewproductFragment()
                        val bundle = Bundle()
                        bundle.putString("idJenisModul", courseId)
                        ReviewproductFragment.arguments = bundle
                        replaceFragment(ReviewproductFragment)
                        isDetailFragmentActive = false
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        // Ganti fragment pertama saat aktivitas dimulai
        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putString("idJenisModul", courseId)
            val detailProductFragment = DetailproductFragment()
            detailProductFragment.arguments = bundle

            replaceFragment(detailProductFragment)
        }

        loadCourseDetail(courseId)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_layoutcoursedetail, fragment)
            .commit()
    }

    private fun loadCourseDetail(courseId: String?) {
        // Buat permintaan ke API untuk mendapatkan detail kursus berdasarkan courseId
        val apiService = Service.apiService
        apiService.getCourseDetail(courseId).enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    val courseList = response.body()
                    if (courseList != null && courseList.isNotEmpty()) {
                        // Pilih item pertama untuk ditampilkan
                        val course = courseList[0]
                        Picasso.get().load(course.gambar).into(gambar)
                    } else {
                        // Tangani jika data kosong
                    }
                } else {
                    // Tangani kesalahan respons dari API (contohnya, respons tidak berhasil)
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
            }
        })
    }
}
