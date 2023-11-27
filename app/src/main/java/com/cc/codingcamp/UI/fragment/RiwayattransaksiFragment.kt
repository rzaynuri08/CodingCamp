package com.cc.codingcamp.UI.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.activity.UnpaidActivity
import com.cc.codingcamp.adapter.RiwayattransaksiAdapter
import com.cc.codingcamp.modal.DataTransaksi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayattransaksiFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var riwayattransaksiAdapter: RiwayattransaksiAdapter
    private lateinit var sharedPreferences: SharedPreferences
    var id_status: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_riwayattransaksi, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewRiwayatTransaksi)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        riwayattransaksiAdapter = RiwayattransaksiAdapter(requireContext(), emptyList())
        recyclerView.adapter = riwayattransaksiAdapter

        recyclerView.adapter = riwayattransaksiAdapter
        riwayattransaksiAdapter.setOnItemClickListener(object : RiwayattransaksiAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedData = riwayattransaksiAdapter.dataSet[position]

                if (selectedData.id_status == "1") {
                    // Navigasi ke DetailTransaksiActivity jika id_status == "1"
                    val intent = Intent(requireContext(), UnpaidActivity::class.java)
                    intent.putExtra("transaksi_id", selectedData.id_transaksi)
                    startActivity(intent)
                } else {
                    Toast.makeText(requireContext(), "ini halaman lain", Toast.LENGTH_SHORT).show()
                }
            }
        })

        val status = arguments?.getString("status")
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")

        // Panggil API untuk mendapatkan data riwayat transaksi
        val apiService = Service.apiService
        val call = apiService.getDataTransaksi(idStatus = status, username = username)

        call.enqueue(object : Callback<List<DataTransaksi>> {
            override fun onResponse(call: Call<List<DataTransaksi>>, response: Response<List<DataTransaksi>>) {
                if (response.isSuccessful) {
                    val dataTransaksiList = response.body()
                    if (dataTransaksiList != null) {
                        if (dataTransaksiList != null && dataTransaksiList.isNotEmpty()){
                            val dataTransaksi = dataTransaksiList[0]
                            id_status = dataTransaksi.id_transaksi
                        }
                        riwayattransaksiAdapter.dataSet = dataTransaksiList
                        riwayattransaksiAdapter.notifyDataSetChanged()
                    } else {
                        // Tangani jika data null atau kosong
                    }
                } else {
                    // Tangani kesalahan respons dari API
                    // Misalnya, tampilkan pesan kesalahan
                    Toast.makeText(requireContext(), "Gagal memuat data riwayat transaksi", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<DataTransaksi>>, t: Throwable) {
                // Tangani kegagalan (misalnya, koneksi gagal)
                // Misalnya, tampilkan pesan kegagalan
                Toast.makeText(requireContext(), "Gagal memuat data riwayat transaksi", Toast.LENGTH_SHORT).show()
            }
        })

        return view
    }
}
