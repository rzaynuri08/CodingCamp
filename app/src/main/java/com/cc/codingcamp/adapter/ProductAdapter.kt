package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Course
import com.squareup.picasso.Picasso

class ProductAdapter(private val context: Context, var courseList: List<Course>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recourse, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val course = courseList[position]

        Picasso.get().load(course.gambar).into(holder.imageView)
        val maxJudulLength = 45 // Jumlah karakter maksimal yang diizinkan untuk judul
        val truncatedJudul = if (course.judul.length > maxJudulLength) {
            course.judul.substring(0, maxJudulLength) + "..."
        } else {
            course.judul
        }
        holder.titleTextView.text = truncatedJudul
        holder.priceTextView.text = "Rp. ${course.harga}"
        holder.idModulTextView.text = course.id_modul

        holder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.gambar)
        var titleTextView: TextView = itemView.findViewById(R.id.judul)
        var priceTextView: TextView = itemView.findViewById(R.id.harga)
        var idModulTextView: TextView = itemView.findViewById(R.id.id_course)
    }
}

