package com.cc.codingcamp.UI.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.RankingAdapter
import com.cc.codingcamp.modal.Ranking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankActivity : AppCompatActivity() {

    private lateinit var rankingAdapter: RankingAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rank)

        recyclerView = findViewById(R.id.recyclerViewRank)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rankingAdapter = RankingAdapter(this, emptyList()) // Initially empty list
        recyclerView.adapter = rankingAdapter

        // Call the API and update the RecyclerView
        getDataFromApi()
    }

    private fun getDataFromApi() {
        val call: Call<List<Ranking>> = Service.apiService.getDataRank()

        call.enqueue(object : Callback<List<Ranking>> {
            override fun onResponse(call: Call<List<Ranking>>, response: Response<List<Ranking>>) {
                if (response.isSuccessful) {
                    val rankingList = response.body()
                    if (rankingList != null) {
                        // Update the RecyclerView with the data from the API
                        rankingAdapter = RankingAdapter(this@RankActivity, rankingList)
                        recyclerView.adapter = rankingAdapter
                    }
                } else {
                    // Handle error
                    // You can show an error message or take appropriate action
                }
            }

            override fun onFailure(call: Call<List<Ranking>>, t: Throwable) {
                // Handle failure
                // You can show an error message or take appropriate action
            }
        })
    }
}
