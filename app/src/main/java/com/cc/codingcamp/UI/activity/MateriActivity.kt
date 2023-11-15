package com.cc.codingcamp.UI.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.MateriAdapter
import com.cc.codingcamp.modal.Materi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MateriActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var materiAdapter: MateriAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_materi)

        recyclerView = findViewById(R.id.MateriRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        materiAdapter = MateriAdapter(emptyList())
        recyclerView.adapter = materiAdapter

        // Ambil data dari API menggunakan Retrofit
        val idSubBab = intent.getStringExtra("id_subbab")
        if (idSubBab != null) {
            fetchData(idSubBab)
        }
    }

    private fun fetchData(idSubBab: String) {
        val call = Service.apiService.getMateri(idSubBab)

        call.enqueue(object : Callback<List<Materi>> {
            override fun onResponse(call: Call<List<Materi>>, response: Response<List<Materi>>) {
                if (response.isSuccessful) {
                    val materiList = response.body() ?: emptyList()
                    materiAdapter = MateriAdapter(materiList)
                    recyclerView.adapter = materiAdapter
                } else {
                    Toast.makeText(this@MateriActivity, "Gagal mengambil data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Materi>>, t: Throwable) {
                Toast.makeText(this@MateriActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
