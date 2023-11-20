package com.cc.codingcamp.UI.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.cc.codingcamp.API.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnMasuk: Button
    private lateinit var btnDaftar: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        edtUsername = view.findViewById(R.id.edt_username)
        edtPassword = view.findViewById(R.id.edt_password)
        btnMasuk = view.findViewById(R.id.btn_masuk)
        btnDaftar = view.findViewById(R.id.btn_signup)

        // Check if user is already logged in
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val username = sharedPreferences.getString("username", "")
            if (!username.isNullOrEmpty()) {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish() // Finish the LoginActivity so that the user can't go back to it using the back button
            }
        }

        btnMasuk.setOnClickListener {
            var username: String
            var password: String

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
                            // Save login state and username to SharedPreferences
                            with(sharedPreferences.edit()) {
                                putBoolean("isLoggedIn", true)
                                putString("username", user.username)
                                apply()
                            }

                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish() // Finish the LoginActivity so that the user can't go back to it using the back button
                            showSuccessToast("Login berhasil. Selamat datang, ${user.nama_lengkap}!")
                        } else {
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
            val registerFragment = RegisterFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_layout, registerFragment)
            transaction.addToBackStack(null)
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