package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.ModulDimiliki
import com.squareup.picasso.Picasso

class ModuldimilikiAdapter(private val context: Context, var modulList: List<ModulDimiliki>) :
    RecyclerView.Adapter<ModuldimilikiAdapter.ModulViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModuldimilikiAdapter.ModulViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_modul, parent, false)
        return ModulViewHolder(view)
    }

    override fun onBindViewHolder(holder: ModuldimilikiAdapter.ModulViewHolder, position: Int) {
        val modul = modulList[position]

        Picasso.get().load(modul.gambar).into(holder.imageView)
        val maxJudulLength = 50 // Jumlah karakter maksimal yang diizinkan untuk judul
        val truncatedJudul = if (modul.judul.length > maxJudulLength) {
            modul.judul.substring(0, maxJudulLength) + "..."
        } else {
            modul.judul
        }
        holder.titleTextView.text = truncatedJudul
        holder.idModulTextView.text = modul.id_modul

        holder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return modulList.size
    }

    inner class ModulViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.gambar)
        var titleTextView: TextView = itemView.findViewById(R.id.judul)
        var idModulTextView: TextView = itemView.findViewById(R.id.id_course)
    }
}
