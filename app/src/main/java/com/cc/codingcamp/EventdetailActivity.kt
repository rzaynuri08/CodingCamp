package com.cc.codingcamp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cc.codingcamp.modal.Event
import com.cc.codingcamp.restApi.Service
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventdetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventdetail)

        // Get eventId from intent
        val eventId = intent.getStringExtra("event_id")

        // Initialize views to display event details
        val gambarImageView = findViewById<ImageView>(R.id.gambar)
        val judulEventTextView = findViewById<TextView>(R.id.juduleventdetail)
        val pelaksanaanTextView = findViewById<TextView>(R.id.pelaksanaan)
        val lokasiTextView = findViewById<TextView>(R.id.lokasi)
        val tanggalTextView = findViewById<TextView>(R.id.tanggal)

        // Make a request to the API to get event details based on eventId
        val apiService = Service.apiService
        apiService.getEventDetail(eventId).enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>, response: Response<List<Event>>) {
                if (response.isSuccessful) {
                    val eventList = response.body()
                    if (eventList != null && eventList.isNotEmpty()) {
                        // Since it's a list, you can pick the first item for display
                        val event = eventList[0]
                        Picasso.get().load(event.gambar).into(gambarImageView)
                        judulEventTextView.text = event.judul_event
                        pelaksanaanTextView.text = event.pelaksanaan
                        lokasiTextView.text = event.lokasi
                        tanggalTextView.text = event.tanggal
                    } else {
                        // Handle if the data is null or empty
                    }
                } else {
                    // Handle API response errors (e.g., unsuccessful response)
                }
            }

            override fun onFailure(call: Call<List<Event>>, t: Throwable) {
                // Handle connection errors or other errors
            }
        })
    }
}
