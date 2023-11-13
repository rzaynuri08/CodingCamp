package com.cc.codingcamp.UI.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Course
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailproductFragment : Fragment() {
    private lateinit var JudulCourse: TextView
    private lateinit var HargaCourse: TextView
    private lateinit var KeteranganCourse: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detailproduct, container, false)

        JudulCourse = view.findViewById(R.id.txt_juduldetailcourse)
        HargaCourse = view.findViewById(R.id.txt_hargadetailcourse)
        KeteranganCourse = view.findViewById(R.id.txt_keterangandetailcourse)

        val apiService = Service.apiService
        val idModul = arguments?.getString("idJenisModul")

        apiService.getCourseDetail(idModul).enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    val courseList = response.body()
                    if (courseList != null && courseList.isNotEmpty()) {
                        // Ambil item pertama (atau sesuaikan dengan kebutuhan Anda)
                        val course = courseList[0]

                        // Tampilkan data pada tampilan sesuai kebutuhan Anda
                        JudulCourse.text = course.judul
                        HargaCourse.text = "Rp. ${course.harga}"
                        KeteranganCourse.text = course.keterangan
                    } else {
                        Toast.makeText(requireContext(), "$idModul", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Tangani kesalahan respons dari API
                }
            }

            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
            }
        })
        return view
    }
}
