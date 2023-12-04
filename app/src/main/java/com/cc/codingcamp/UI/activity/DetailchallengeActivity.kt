package com.cc.codingcamp.UI.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.MyChallengeAdapter
import com.cc.codingcamp.modal.MyChallenge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailchallengeActivity : AppCompatActivity() {

    private lateinit var recyclerViewMyChallenge: RecyclerView
    private lateinit var myChallengeAdapter: MyChallengeAdapter
    private lateinit var spinnerStatus: Spinner
    private var selectedStatusId: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailchallenge)

        recyclerViewMyChallenge = findViewById(R.id.recyclerViewMyChallenge)
        recyclerViewMyChallenge.layoutManager = LinearLayoutManager(this)
        myChallengeAdapter = MyChallengeAdapter(this, emptyList())
        recyclerViewMyChallenge.adapter = myChallengeAdapter

        spinnerStatus = findViewById(R.id.spinnerStatusId)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        // Data untuk Spinner
        val statusArray = arrayOf("Diproses", "Berhasil", "Gagal")
        val statusMap = mapOf("Diproses" to "1", "Berhasil" to "2", "Gagal" to "3")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, statusArray)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerStatus.adapter = adapter

        // Menangani perubahan nilai pada Spinner
        spinnerStatus.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                // Panggil API untuk mendapatkan data MyChallenge berdasarkan username dan statusId

                val selectedStatus = statusArray[position]
                selectedStatusId = statusMap[selectedStatus]
                if (selectedStatusId != null) {
                    if (username != null) {
                        fetchData(username, selectedStatusId!!)
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Handle when nothing is selected
            }
        })

        // Panggil API untuk mendapatkan data MyChallenge dengan statusId awal (misalnya, "1")
        if (username != null) {
            fetchData(username, "1")
        }
    }

    private fun fetchData(username: String, statusId: String) {
        val apiService = Service.apiService
        apiService.getDataMychallenge(username, statusId).enqueue(object : Callback<List<MyChallenge>> {
            override fun onResponse(call: Call<List<MyChallenge>>, response: Response<List<MyChallenge>>) {
                if (response.isSuccessful) {
                    val myChallengeList = response.body()
                    if (myChallengeList != null) {
                        myChallengeAdapter.myChallengeList = myChallengeList
                        myChallengeAdapter.notifyDataSetChanged()
                    } else {
                        // Handle if data is null or empty
                    }
                } else {
                    // Handle API response errors (e.g., unsuccessful response)
                }
            }

            override fun onFailure(call: Call<List<MyChallenge>>, t: Throwable) {
                // Handle connection errors or other errors
            }
        })
    }
}