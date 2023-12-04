package com.cc.codingcamp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.MyChallenge

class MyChallengeAdapter(private val context: Context, var myChallengeList: List<MyChallenge>) :
    RecyclerView.Adapter<MyChallengeAdapter.MyChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChallengeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mychallenge, parent, false)
        return MyChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyChallengeViewHolder, position: Int) {
        val myChallenge = myChallengeList[position]

        holder.txtJenisBahasa.text = myChallenge.nama_jenis
        holder.txtJenisLevel.text = myChallenge.jenis_lvl
        holder.txtIdStatus.text = myChallenge.status_submit
        holder.txtSoal.text = myChallenge.soal
    }

    override fun getItemCount(): Int {
        return myChallengeList.size
    }

    class MyChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtJenisBahasa: TextView = itemView.findViewById(R.id.txt_jenisbahasachallenge)
        val txtJenisLevel: TextView = itemView.findViewById(R.id.txt_jenislevelchallenge)
        val txtIdStatus: TextView = itemView.findViewById(R.id.txt_txt_idstatus)
        val txtSoal: TextView = itemView.findViewById(R.id.txt_soalchallenge)
    }
}
