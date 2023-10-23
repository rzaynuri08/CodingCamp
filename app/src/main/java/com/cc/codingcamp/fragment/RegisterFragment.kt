package com.cc.codingcamp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction
import com.cc.codingcamp.R

// TODO: Rename parameter arguments, choose names that match
class RegisterFragment : Fragment() {
    private lateinit var btnMasuk: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        btnMasuk = view.findViewById(R.id.btn_signin)

        btnMasuk.setOnClickListener {
            // Membuat instance RegisterFragment
            val LoginFragment = LoginFragment()

            // Memulai transaksi fragment untuk mengganti fragment
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_layout, LoginFragment) // Ganti dengan ID container Anda
            transaction.commit()
        }
        return view
    }
}