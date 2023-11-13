package com.cc.codingcamp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.MainPreview
import com.cc.codingcamp.databinding.ItemParentpreviewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpandableRecyclerViewAdapter(private var mainPreviews: List<MainPreview>) :
    RecyclerView.Adapter<ExpandableRecyclerViewAdapter.ViewHolder>() {

    private var expandedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parentpreview, parent, false)
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
        private val binding = ItemParentpreviewBinding.bind(itemView)

        fun bind(mainPreview: MainPreview, isExpanded: Boolean) {
            binding.judulPreview.text = mainPreview.nama_bab

            // Set the visibility of SubItem based on the expanded state
            binding.SubItem.visibility = if (isExpanded) View.VISIBLE else View.GONE

            if (mainPreview.subItemModels.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    val subPreviewAdapter = SubPreviewAdapter(mainPreview.subItemModels)
                    binding.SubItem.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = subPreviewAdapter
                    }
                }
            }
        }
    }

    fun updateData(newData: List<MainPreview>) {
        mainPreviews = newData
        notifyDataSetChanged()
    }
}
