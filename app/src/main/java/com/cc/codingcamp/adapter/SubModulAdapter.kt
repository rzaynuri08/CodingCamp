package com.cc.codingcamp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.databinding.ItemSubmodulBinding
import com.cc.codingcamp.modal.SubPreview

class SubModulAdapter(private val subPreviews: List<SubPreview>) : RecyclerView.Adapter<SubModulAdapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(subPreview: SubPreview)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_submodul, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return subPreviews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subPreviews[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSubmodulBinding.bind(itemView)

        fun bind(subPreview: SubPreview) {
            binding.idSubBab.text = subPreview.id_subbab
            binding.judulSubItem.text = subPreview.nama_subbab
            binding.idBabInSub.text = subPreview.id_bab

            // Set click listener
            itemView.setOnClickListener {
                mListener.onItemClick(subPreview)
            }
        }
    }
}