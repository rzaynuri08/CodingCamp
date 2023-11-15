package com.cc.codingcamp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Materi
import com.squareup.picasso.Picasso

class MateriAdapter(private val materiList: List<Materi>) : RecyclerView.Adapter<MateriAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judulMateri: TextView = itemView.findViewById(R.id.judulmateri)
        val materiContent: TextView = itemView.findViewById(R.id.materi)
        val gambarContent: ImageView = itemView.findViewById(R.id.gambarMateri)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_materi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return materiList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val materi = materiList[position]

        // Set data to views
        holder.judulMateri.text = materi.judul
        holder.materiContent.text = materi.materi

        // Menggunakan Picasso untuk memuat gambar dari URL ke ImageView
        if (materi.gambar.isNotEmpty()) {
            Picasso.get().load(materi.gambar).into(holder.gambarContent)
        } else {
            holder.gambarContent.visibility = View.GONE
        }
    }
}