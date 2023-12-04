package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Ranking

class RankingAdapter(private val context: Context, private val rankingList: List<Ranking>) :
    RecyclerView.Adapter<RankingAdapter.RankingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_userrank, parent, false)
        return RankingViewHolder(view)
    }

    override fun onBindViewHolder(holder: RankingViewHolder, position: Int) {
        val ranking = rankingList[position]

        // Set rank number
        holder.txtRank.text = "#${(position + 1).toString()}"
        holder.txtNamaUser.text = ranking.username
        holder.txtTrophy.text = ranking.tropi
        Glide.with(context)
            .load(ranking.foto_profil)
            .into(holder.imgFotoProfil)

    }

    override fun getItemCount(): Int {
        return rankingList.size
    }

    class RankingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtRank: TextView = itemView.findViewById(R.id.txt_rank)
        val imgFotoProfil: ImageView = itemView.findViewById(R.id.img_fotoprofil)
        val txtNamaUser: TextView = itemView.findViewById(R.id.txt_namauser)
        val txtTrophy: TextView = itemView.findViewById(R.id.txt_trophy)
    }
}
