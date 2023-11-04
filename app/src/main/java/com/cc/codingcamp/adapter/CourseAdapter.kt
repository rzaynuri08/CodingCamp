package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.cc.codingcamp.modal.Course
import com.cc.codingcamp.R

class CourseAdapter(private val context: Context, var courseList: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_course, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courseList[position]

        holder.judulCourseTextView.text = course.judul
        holder.hargaCourseTextView.text = "Rp. ${course.harga}"

        // Menggunakan Picasso untuk memuat gambar dari URL ke ImageView
        Picasso.get().load(course.gambar).into(holder.gambarCourseImageView)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judulCourseTextView: TextView = itemView.findViewById(R.id.txt_judulcourse)
        val hargaCourseTextView: TextView = itemView.findViewById(R.id.txt_hargacourse)
        val gambarCourseImageView: ImageView = itemView.findViewById(R.id.img_gambarcourse)
    }
}