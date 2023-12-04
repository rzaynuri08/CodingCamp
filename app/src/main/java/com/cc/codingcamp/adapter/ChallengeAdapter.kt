package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Challenge
class ChallengeAdapter(private val context: Context, var challengeList: List<Challenge>) :
    RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    inner class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idChallenge: TextView = itemView.findViewById(R.id.txt_idchallenge)
        val jenisBahasaChallenge: TextView = itemView.findViewById(R.id.txt_jenisbahasachallenge)
        val jenisLevelChallenge: TextView = itemView.findViewById(R.id.txt_jenislevelchallenge)
        val kuotaChallenge: TextView = itemView.findViewById(R.id.txt_kuotachallenge)
        val soalChallenge: TextView = itemView.findViewById(R.id.txt_soalchallenge)
        val koinDidapatkan: TextView = itemView.findViewById(R.id.txt_koindidapatkan)
        val trophyChallenge: TextView = itemView.findViewById(R.id.txt_trophychallenge)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_challenge, parent, false)
        return ChallengeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val currentChallenge = challengeList[position]

        holder.idChallenge.text = currentChallenge.id_challenge
        holder.jenisBahasaChallenge.text = "#${currentChallenge.nama_jenis}"
        holder.jenisLevelChallenge.text = "#${currentChallenge.jenis_lvl}"
        holder.kuotaChallenge.text = currentChallenge.kuota
        holder.soalChallenge.text = currentChallenge.soal
        holder.koinDidapatkan.text = currentChallenge.koin
        holder.trophyChallenge.text = currentChallenge.tropi

        holder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return challengeList.size
    }
}