package com.cc.codingcamp.UI.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cc.codingcamp.modal.Event
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventdetailActivity : AppCompatActivity() {
    private lateinit var btnUrl: Button
    private var url: String = ""
    private lateinit var gambarImageView: ImageView
    private lateinit var judulEventTextView: TextView
    private lateinit var pelaksanaanTextView: TextView
    private lateinit var lokasiTextView: TextView
    private lateinit var tanggalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventdetail)

        // Get eventId from intent
        val eventId = intent.getStringExtra("event_id")

        // Initialize views to display event details
        gambarImageView = findViewById(R.id.gambar)
        judulEventTextView = findViewById(R.id.juduleventdetail)
        pelaksanaanTextView = findViewById(R.id.pelaksanaan)
        lokasiTextView = findViewById(R.id.lokasi)
        tanggalTextView = findViewById(R.id.tanggal)

        btnUrl = findViewById(R.id.btn_url)
        btnUrl.setOnClickListener {
            val intent = Intent(android.content.Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            intent.`package` = "com.android.chrome"

            if (url.isNotEmpty() && url.startsWith("http")) {
                startActivity(intent)
            } else {
                // Handle jika url tidak tersedia
                Toast.makeText(this, "mohon maaf, url tidak valid", Toast.LENGTH_SHORT).show()
            }
        }

        eventViewDetail(eventId)
    }

    private fun eventViewDetail(eventId: String?) {
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
                        url = event.link_pendaftaran
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
