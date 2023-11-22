package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.MetodePembayaran
import com.squareup.picasso.Picasso

class MetodePembayaranAdapter(
    private val context: Context,
    private val dataList: List<MetodePembayaran>,
    private val clickListener: (String, String, String) -> Unit
) : RecyclerView.Adapter<MetodePembayaranAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cardViewMetodePembayaran)
        val idPembayaran: TextView = itemView.findViewById(R.id.txt_idPembayaran)
        val logoJenisPembayaran: ImageView = itemView.findViewById(R.id.txt_logojenispembayaran)
        val namaJenisPembayaran: TextView = itemView.findViewById(R.id.txt_namajenispembayaran)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_jenispembayaran, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val metodePembayaran = dataList[position]

        holder.idPembayaran.text = metodePembayaran.id_pembayaran
        holder.namaJenisPembayaran.text = metodePembayaran.nama_pembayaran

        // Load image using Picasso
        Picasso.get().load(metodePembayaran.icon).into(holder.logoJenisPembayaran)

        // Handle item click
        holder.cardView.setOnClickListener {
            clickListener.invoke(metodePembayaran.id_pembayaran, metodePembayaran.nama_pembayaran, metodePembayaran.icon)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}