package com.cc.codingcamp.fragment

import EventAdapter
import android.content.Intent
import android.icu.text.Transliterator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cc.codingcamp.CourseViewActivity
import com.cc.codingcamp.EventdetailActivity
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Event
import com.cc.codingcamp.restApi.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.icu.text.Transliterator.Position as position

class EventFragment : Fragment() {
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventAdapter: EventAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)

        eventRecyclerView = view.findViewById(R.id.eventRecyclerView)
        eventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        eventAdapter = EventAdapter(requireContext(), emptyList())

        // SwipeRefreshLayout
        swipeRefreshLayout = view.findViewById(R.id.SwipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = true

            // Load data acara
            loadEventData()

            // Hentikan animasi segar setelah 5000 milidetik (5 detik)
            Handler().postDelayed({
                swipeRefreshLayout.isRefreshing = false
            }, 500L)
        }

        loadEventData() // Fungsi untuk memuat data acara

        eventRecyclerView.adapter = eventAdapter
        eventAdapter.setOnItemClickListener(object : EventAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val event = eventAdapter.eventList[position]
                val intent = Intent(requireContext(), EventdetailActivity::class.java)
                intent.putExtra("event_id", event.id_event)
                Toast.makeText(requireContext(), "id event : ${event.id_event}", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
        })

        return view
    }

    private fun loadEventData() {
        // Ambil data acara dari API menggunakan Retrofit
        val apiService = Service.apiService // Sesuaikan dengan objek ApiService yang ada di proyek Anda
        apiService.getEvent().enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val eventList = response.body()
                    if (eventList != null) {
                        eventAdapter.eventList = eventList
                        eventAdapter.notifyDataSetChanged()
                    } else {
                        // Tangani jika data null atau kosong
                    }
                } else {
                    // Handle kesalahan respons dari API (misalnya respons tidak sukses)
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                // Handle kesalahan koneksi atau kesalahan lainnya
            }
        })
    }
}
