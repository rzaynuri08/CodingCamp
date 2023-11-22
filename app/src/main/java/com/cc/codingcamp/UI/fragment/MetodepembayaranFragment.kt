package com.cc.codingcamp.UI.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.MetodePembayaranAdapter
import com.cc.codingcamp.modal.MetodePembayaran
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MetodepembayaranFragment : DialogFragment() {

    interface MetodePembayaranClickListener {
        fun onMetodePembayaranClicked(idPembayaran: String, namaPembayaran: String, icon: String)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MetodePembayaranAdapter
    private lateinit var clickListener: MetodePembayaranClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_metodepembayaran, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewMetodePembayaran)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Fetch data from API
        fetchMetodePembayaran()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Check if the host activity implements the interface
        if (activity is MetodePembayaranClickListener) {
            clickListener = activity as MetodePembayaranClickListener
        } else {
            throw ClassCastException("Host activity must implement MetodePembayaranClickListener")
        }
    }

    private fun fetchMetodePembayaran() {
        val apiService = Service.apiService

        apiService.getMetodePembayaran().enqueue(object : Callback<List<MetodePembayaran>> {
            override fun onResponse(
                call: Call<List<MetodePembayaran>>,
                response: Response<List<MetodePembayaran>>
            ) {
                if (response.isSuccessful) {
                    val metodePembayaranList = response.body()
                    if (metodePembayaranList != null) {
                        // Set up the adapter with the data
                        adapter = MetodePembayaranAdapter(requireContext(), metodePembayaranList) { idPembayaran, namaPembayaran, icon ->
                            clickListener.onMetodePembayaranClicked(idPembayaran, namaPembayaran, icon)
                            dismiss()
                        }
                        recyclerView.adapter = adapter
                    }
                } else {
                    // Handle error response
                }
            }

            override fun onFailure(call: Call<List<MetodePembayaran>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}
