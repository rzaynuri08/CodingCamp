package com.cc.codingcamp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.databinding.ItemParentmodulBinding
import com.cc.codingcamp.modal.MainPreview
import com.cc.codingcamp.databinding.ItemParentpreviewBinding

class MainModulAdapter(private val mainPreviews: List<MainPreview>) : RecyclerView.Adapter<MainModulAdapter.ViewHolder>() {
    private var expandedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_parentmodul, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mainPreviews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isExpanded = position == expandedPosition
        holder.bind(mainPreviews[position], isExpanded)
        holder.itemView.setOnClickListener {
            expandedPosition = if (isExpanded) RecyclerView.NO_POSITION else position
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemParentmodulBinding.bind(itemView)

        fun bind(mainPreview: MainPreview, isExpanded: Boolean) {
            binding.idBab.text = mainPreview.id_bab
            binding.judulPreview.text = mainPreview.nama_bab

            // Set the visibility of SubItem based on the expanded state
            binding.SubItem.visibility = if (isExpanded) View.VISIBLE else View.GONE

            val subPreviewAdapter = SubPreviewAdapter(mainPreview.subItemModels)
            binding.SubItem.adapter = subPreviewAdapter
        }
    }
}