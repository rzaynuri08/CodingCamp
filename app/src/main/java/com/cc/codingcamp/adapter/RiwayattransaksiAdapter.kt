package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.DataTransaksi
import com.squareup.picasso.Picasso

class RiwayattransaksiAdapter(context: Context, var dataSet: List<DataTransaksi>) :
    RecyclerView.Adapter<RiwayattransaksiAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val jenisStatus: TextView = view.findViewById(R.id.txt_jenisstatus)
        val judul: TextView = view.findViewById(R.id.txt_judul)
        val subtotal: TextView = view.findViewById(R.id.txt_subtotal)
        val total: TextView = view.findViewById(R.id.txt_total)
        val gambar: ImageView = view.findViewById(R.id.img_gambar)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_datatransaksi, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dataTransaksi = dataSet[position]

        viewHolder.jenisStatus.text = dataTransaksi.jenis_status
        viewHolder.judul.text = dataTransaksi.judul
        viewHolder.subtotal.text = "Rp. ${dataTransaksi.subtotal}"
        viewHolder.total.text = "Rp. ${dataTransaksi.total}"
        Picasso.get()
            .load(dataTransaksi.gambar)
            .into(viewHolder.gambar)

        viewHolder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}