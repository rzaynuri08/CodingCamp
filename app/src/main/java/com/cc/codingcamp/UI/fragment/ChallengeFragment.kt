package com.cc.codingcamp.UI.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.activity.ChallengeActivity
import com.cc.codingcamp.UI.activity.DetailchallengeActivity
import com.cc.codingcamp.UI.activity.ModuldetailActivity
import com.cc.codingcamp.UI.activity.RankActivity
import com.cc.codingcamp.adapter.ChallengeAdapter
import com.cc.codingcamp.adapter.ModuldimilikiAdapter
import com.cc.codingcamp.modal.Challenge
import com.cc.codingcamp.modal.MyChallenge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeFragment : Fragment() {

    private lateinit var recyclerViewChallenge: RecyclerView
    private lateinit var challengeAdapter: ChallengeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_challenge, container, false)

        // Inisialisasi dan atur RecyclerView untuk Challenge
        recyclerViewChallenge = view.findViewById(R.id.recyclerViewChallenge)
        recyclerViewChallenge.layoutManager = LinearLayoutManager(requireContext())
        challengeAdapter = ChallengeAdapter(requireContext(), emptyList())
        recyclerViewChallenge.adapter = challengeAdapter

        val btnRank = view.findViewById<ImageButton>(R.id.btn_rank)
        btnRank.setOnClickListener{
            val intent = Intent(requireContext(), RankActivity::class.java)
            startActivity(intent)
        }
        val btnMyChallenge = view.findViewById<ImageButton>(R.id.btn_mychallenge)
        btnMyChallenge.setOnClickListener{
            val intent = Intent(requireContext(), DetailchallengeActivity::class.java)
            startActivity(intent)
        }

        challengeAdapter.setOnItemClickListener(object : ChallengeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedCourse = challengeAdapter.challengeList[position]

                val intent = Intent(requireContext(), ChallengeActivity::class.java)
                intent.putExtra("challenge_id", selectedCourse.id_challenge)
                startActivity(intent)
            }
        })

        // Panggil API untuk mendapatkan data Challenge
        val apiService = Service.apiService
        apiService.getChallenge().enqueue(object : Callback<List<Challenge>> {
            override fun onResponse(
                call: Call<List<Challenge>>,
                response: Response<List<Challenge>>
            ) {
                if (response.isSuccessful) {
                    val challengeList = response.body()
                    if (challengeList != null) {
                        challengeAdapter.challengeList = challengeList
                        challengeAdapter.notifyDataSetChanged()
                    } else {
                        // Tangani jika data null atau kosong
                    }
                } else {
                    // Tangani kesalahan respons dari API
                }
            }

            override fun onFailure(call: Call<List<Challenge>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
            }
        })

        // Tambahkan event click jika diperlukan

        return view
    }
}