package com.cc.codingcamp.UI.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.LoginActivity
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.User
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private lateinit var btnLogOut: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        btnLogOut = view.findViewById(R.id.btn_LogOut)

        sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        btnLogOut.setOnClickListener {
            // Clear SharedPreferences
            with(sharedPreferences.edit()) {
                clear()
                apply()
            }

            // Navigate to LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        val username = sharedPreferences.getString("username", "")

        Service.apiService.getSession(username).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    if (userList != null && userList.isNotEmpty()) {
                        val user = userList[0]
                        val txtEmail = view.findViewById<TextView>(R.id.txt_email)
                        val txtNama = view.findViewById<TextView>(R.id.txt_nama)
                        val fotoProfile = view.findViewById<ImageView>(R.id.txt_homefotoprofil)
                        Picasso.get().load(user.foto_profil).into(fotoProfile)
                        txtEmail.text = user.email
                        txtNama.text = user.nama_lengkap
                    }
                } else {
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {

            }
        })

        return view
    }
}
