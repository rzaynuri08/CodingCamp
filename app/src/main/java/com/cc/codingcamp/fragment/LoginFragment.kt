package com.cc.codingcamp.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.cc.codingcamp.MainActivity
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.User
import com.test.learnactivity.API.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnMasuk: Button
    private lateinit var btnDaftar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        edtUsername = view.findViewById(R.id.edt_username)
        edtPassword = view.findViewById(R.id.edt_password)
        btnMasuk = view.findViewById(R.id.btn_masuk)
        btnDaftar = view.findViewById(R.id.btn_signup)

        // Deklarasikan variabel untuk username dan password di sini
        var username: String
        var password: String

        btnMasuk.setOnClickListener {
            // Dapatkan username dan password dari EditText
            username = edtUsername.text.toString()
            password = edtPassword.text.toString()

            val apiService = Service.apiService
            val call = apiService.getUsers()

            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val users = response.body()
                        val user = users?.find { it.username == username && it.password == password }
                        if (user != null) {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            showSuccessToast("Login berhasil. Selamat datang, ${user.nama_lengkap}!")
                        } else {
                            // Login gagal, tampilkan pesan kesalahan menggunakan Toast
                            showErrorToast("Login gagal. Cek kembali username dan password Anda.")
                        }
                    }
                }

                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    showErrorToast("Server tidak ditemukan")
                }
            })
        }
        btnDaftar.setOnClickListener {
            // Membuat instance RegisterFragment
            val registerFragment = RegisterFragment()

            // Memulai transaksi fragment untuk mengganti fragment
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_layout, registerFragment) // Ganti dengan ID container Anda
            transaction.addToBackStack(null) // Menambahkan fragment ke back stack
            transaction.commit()
        }
        return view
    }
    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showSuccessToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}