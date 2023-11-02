package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.JenisModul
import com.squareup.picasso.Picasso

class JenisModulAdapter(private val context: Context, var jenisModulList: List<JenisModul>) :
    RecyclerView.Adapter<JenisModulAdapter.JenisModulViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JenisModulViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.data_jenismodul, parent, false)
        return JenisModulViewHolder(view)
    }

    override fun onBindViewHolder(holder: JenisModulViewHolder, position: Int) {
        val jenisModul = jenisModulList[position]

        // Menggunakan Picasso untuk memuat gambar dari URL ke ImageView
        Picasso.get().load(jenisModul.gambar).into(holder.gambarImageView)

        holder.namaJenisTextView.text = jenisModul.nama_jenis
    }

    override fun getItemCount(): Int {
        return jenisModulList.size
    }

    inner class JenisModulViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val gambarImageView: ImageView = itemView.findViewById(R.id.gambar)
        val namaJenisTextView: TextView = itemView.findViewById(R.id.nama_jenis)
    }
}
