package com.cc.codingcamp.UI.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.UI.activity.CourseViewActivity
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.JenisModulAdapter
import com.cc.codingcamp.adapter.ModuldimilikiAdapter
import com.cc.codingcamp.modal.JenisModul
import com.cc.codingcamp.modal.ModulDimiliki
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.UI.activity.PaymentActivity
import com.cc.codingcamp.UI.activity.ModuldetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CourseFragment : Fragment() {

    private lateinit var recyclerViewJenisModul: RecyclerView
    private lateinit var jenisModulAdapter: JenisModulAdapter
    private lateinit var recyclerViewModulDimiliki: RecyclerView
    private lateinit var moduldimilikiAdapter: ModuldimilikiAdapter
    private lateinit var btnTransaksi: ImageButton
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_course, container, false)

        // Inisialisasi dan atur RecyclerView untuk Jenis Modul
        recyclerViewJenisModul = view.findViewById(R.id.recyclerViewJenisModul)
        recyclerViewJenisModul.layoutManager = GridLayoutManager(requireContext(), 3)
        jenisModulAdapter = JenisModulAdapter(requireContext(), emptyList())
        recyclerViewJenisModul.adapter = jenisModulAdapter
        btnTransaksi = view.findViewById(R.id.btn_allTransaksi)

        // Panggil API untuk mendapatkan data JenisModul
        val apiServiceJenisModul = Service.apiService
        apiServiceJenisModul.getJenisModul().enqueue(object : Callback<List<JenisModul>> {
            override fun onResponse(
                call: Call<List<JenisModul>>,
                response: Response<List<JenisModul>>
            ) {
                if (response.isSuccessful) {
                    val jenisModulList = response.body()
                    if (jenisModulList != null) {
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

        val userLog = fetchUserData()

        jenisModulAdapter.setOnItemClickListener(object : JenisModulAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val jenisModul = jenisModulAdapter.jenisModulList[position]
                val intent = Intent(requireContext(), CourseViewActivity::class.java)
                intent.putExtra("id_jenismodul", jenisModul.id_jenismodul)
                intent.putExtra("nama_jenis", jenisModul.nama_jenis)

                Toast.makeText(
                    requireContext(),
                    "Klik pada item dengan id_jenismodul: ${jenisModul.id_jenismodul} nama jenis: ${jenisModul.nama_jenis}",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(intent)
            }
        })

        // Inisialisasi dan atur RecyclerView untuk Modul Dimiliki
        recyclerViewModulDimiliki = view.findViewById(R.id.recyclerViewModulDimiliki)
        recyclerViewModulDimiliki.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        moduldimilikiAdapter = ModuldimilikiAdapter(requireContext(), emptyList())
        recyclerViewModulDimiliki.adapter = moduldimilikiAdapter

        moduldimilikiAdapter.setOnItemClickListener(object : ModuldimilikiAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedCourse = moduldimilikiAdapter.modulList[position]

                // Misalnya, Anda ingin membuka detail kursus saat item diklik
                val intent = Intent(requireContext(), ModuldetailActivity::class.java)
                intent.putExtra("course_id", selectedCourse.id_modul)
                startActivity(intent)
            }
        })

        btnTransaksi.setOnClickListener{
            val intent = Intent(requireContext(), PaymentActivity::class.java)
            startActivity(intent)
        }

        loadModulData(userLog)

        return view
    }

    private fun fetchUserData(): String? {
        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("username", "")
    }

    private fun loadModulData(userLog: String?) {
        val apiServiceModul = Service.apiService

        apiServiceModul.getModulDimiliki(userLog).enqueue(object : Callback<List<ModulDimiliki>> {
            override fun onResponse(
                call: Call<List<ModulDimiliki>>,
                response: Response<List<ModulDimiliki>>
            ) {
                if (response.isSuccessful) {
                    val modulList = response.body()
                    if (modulList != null) {
                        moduldimilikiAdapter.modulList = modulList
                        moduldimilikiAdapter.notifyDataSetChanged()
                    } else {
                        // Tangani jika data null atau kosong
                    }
                } else {
                    // Tangani kesalahan respons dari API (misalnya respons tidak sukses)
                }
            }

            override fun onFailure(call: Call<List<ModulDimiliki>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
            }
        })
    }
}