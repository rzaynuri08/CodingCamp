package com.cc.codingcamp.UI.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.API.Service.apiService
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.Challenge
import com.cc.codingcamp.modal.ResponseModel
import com.cc.codingcamp.modal.SubmitChallenge
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeActivity : AppCompatActivity() {
    private lateinit var txtLink: EditText
    private lateinit var txtKeterangan: EditText
    private lateinit var txtSoal: TextView
    private lateinit var txtJenis: TextView
    private lateinit var txtLevel: TextView
    private lateinit var btnKumpulkan: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        txtLink = findViewById(R.id.txt_link)
        txtKeterangan = findViewById(R.id.txt_keterangan)
        txtSoal = findViewById(R.id.txt_soal)
        txtJenis = findViewById(R.id.txt_jenis)
        txtLevel = findViewById(R.id.txt_level)
        btnKumpulkan = findViewById(R.id.btn_kumpulkan)

        btnKumpulkan.setOnClickListener{
            val challengeId = intent.getStringExtra("challenge_id")
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val username = sharedPreferences.getString("username", "")
            val submitChallenge = SubmitChallenge(username = username, id_challenge = challengeId, linkpengumpulan = txtLink.text.toString(), keterangan = txtKeterangan.text.toString())

            sendSubmitChallenge(submitChallenge)
        }

        val challengeId = intent.getStringExtra("challenge_id")

        fetchData(challengeId)
    }

    fun fetchData(challengeId: String?){
        val apiService = Service.apiService
        val call = apiService.getChallengeView(challengeId)

        call.enqueue(object : Callback<List<Challenge>> {
            override fun onResponse(call: Call<List<Challenge>>, response: Response<List<Challenge>>) {
                if (response.isSuccessful) {
                    val challengeList = response.body()
                    if (challengeList != null && challengeList.isNotEmpty()) {
                        val dataChallenge = challengeList[0]
                        txtSoal.text = dataChallenge.soal
                        txtJenis.text = dataChallenge.nama_jenis
                        txtLevel.text = dataChallenge.jenis_lvl
                    } else {

                    }
                } else {

                }
            }

            override fun onFailure(call: Call<List<Challenge>>, t: Throwable) {
                // Tangani kegagalan (misalnya, koneksi gagal)
                // Misalnya, tampilkan pesan kegagalan
            }
        })
    }

    fun sendSubmitChallenge(submitChallenge: SubmitChallenge){
        val submitChallengeCall = apiService.submitChallenge(submitChallenge)

        submitChallengeCall.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    Toast.makeText(this@ChallengeActivity, "berhasil", Toast.LENGTH_SHORT).show()
                    // Tangani respons yang berhasil di sini
                } else {
                    // Tangani respons gagal di sini
                    Toast.makeText(this@ChallengeActivity, "tidak berhasil", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                // Tangani kegagalan koneksi atau respons di sini
            }
        })
    }
}