package com.cc.codingcamp.UI.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.fragment.RiwayattransaksiFragment

class Riwayattransaksi_Activity : AppCompatActivity() {
    private lateinit var btnBack: ImageButton
    private lateinit var btnBelumDibayar: Button
    private lateinit var btnDiproses: Button
    private lateinit var btnSelesai: Button
    private lateinit var btnDitolak: Button
    private lateinit var btnDibatalkan: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayattransaksi)

        // Inisialisasi View
        btnBack = findViewById(R.id.btn_back)
        btnBelumDibayar = findViewById(R.id.btn_belumbayar)
        btnDiproses = findViewById(R.id.btn_diproses)
        btnSelesai = findViewById(R.id.btn_selesai)
        btnDitolak = findViewById(R.id.btn_ditolak)
        btnDibatalkan = findViewById(R.id.btn_dibatalkan)

        // Fungsionalitas button
        btnBack.setOnClickListener{
            finish()
        }

        val defaultFragment = RiwayattransaksiFragment()
        val defaultBundle = Bundle()
        val defaultStatus = "1"
        defaultBundle.putString("status", defaultStatus)
        defaultFragment.arguments = defaultBundle

        val defaultTransaction = supportFragmentManager.beginTransaction()
        defaultTransaction.replace(R.id.Frame_riwayattransaksi, defaultFragment)
        defaultTransaction.commit()

        btnBelumDibayar.setOnClickListener{
            val fragment = RiwayattransaksiFragment()
            val bundle = Bundle()
            val status = "1"

            bundle.putString("status", status)
            fragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Frame_riwayattransaksi, fragment)
            transaction.commit()
        }

        btnDiproses.setOnClickListener{
            val fragment = RiwayattransaksiFragment()
            val bundle = Bundle()
            val status = "2"
            bundle.putString("status", status)
            fragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Frame_riwayattransaksi, fragment)
            transaction.commit()
        }

        btnSelesai.setOnClickListener{
            val fragment = RiwayattransaksiFragment()
            val bundle = Bundle()
            val status = "3"
            bundle.putString("status", status)
            fragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Frame_riwayattransaksi, fragment)
            transaction.commit()
        }

        btnDitolak.setOnClickListener{
            val fragment = RiwayattransaksiFragment()
            val bundle = Bundle()
            val status = "4"
            bundle.putString("status", status)
            fragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Frame_riwayattransaksi, fragment)
            transaction.commit()
        }

        btnDibatalkan.setOnClickListener{
            val fragment = RiwayattransaksiFragment()
            val bundle = Bundle()
            val status = "5"
            bundle.putString("status", status)
            fragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.Frame_riwayattransaksi, fragment)
            transaction.commit()
        }
    }
}