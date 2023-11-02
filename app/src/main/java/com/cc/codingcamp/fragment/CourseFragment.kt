package com.cc.codingcamp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.JenisModulAdapter
import com.cc.codingcamp.modal.JenisModul
import com.test.learnactivity.API.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var jenisModulAdapter: JenisModulAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course, container, false)

        recyclerView = view.findViewById(R.id.recyclerView) // Pastikan ID sesuai dengan layout XML Anda.

        // Inisialisasi adapter
        jenisModulAdapter = JenisModulAdapter(requireContext(), ArrayList()) // Anda dapat mengganti ArrayList() dengan data yang sesuai.

        // Hubungkan adapter dengan RecyclerView dan atur GridLayoutManager
        recyclerView.adapter = jenisModulAdapter
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3) // Grid dengan span 3

        // Panggil API untuk mendapatkan data JenisModul
        val apiService = Service.apiService
        apiService.getCourse().enqueue(object : Callback<List<JenisModul>> {
            override fun onResponse(call: Call<List<JenisModul>>, response: Response<List<JenisModul>>) {
                if (response.isSuccessful) {
                    val jenisModulList = response.body()
                    if (jenisModulList != null) {
                        // Perbarui data yang ada di adapter
                        jenisModulAdapter.jenisModulList = jenisModulList
                        jenisModulAdapter.notifyDataSetChanged()
                    } else {
                        // Tangani jika data null atau kosong
                    }
                } else {
                    // Tangani kesalahan respons dari API
                }
            }

            override fun onFailure(call: Call<List<JenisModul>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
            }
        })

        return view
    }
}
