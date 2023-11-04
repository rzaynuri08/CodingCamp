package com.cc.codingcamp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.cc.codingcamp.R
import com.cc.codingcamp.modal.ResponseModel
import com.cc.codingcamp.modal.Register
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.cc.codingcamp.restApi.Service
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
@Suppress("DEPRECATION")
class RegisterFragment : Fragment() {
    private lateinit var btnMasuk: Button
    private lateinit var btnDaftar: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        btnMasuk = view.findViewById(R.id.btn_signin)
        btnDaftar = view.findViewById(R.id.btn_register)

        val noHpEditText = view.findViewById<TextInputEditText>(R.id.txt_nohp)
        noHpEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                val text = editable.toString()

                if (text.startsWith("+62")) {
                    // Jika diawali dengan +62, hapus karakter +62
                    val filteredText = text.substring(3)
                    noHpEditText.setText(filteredText)
                    noHpEditText.setSelection(noHpEditText.text?.length ?: 0)
                } else if (text.startsWith("0")) {
                    // Jika diawali dengan 0, hapus karakter 0
                    val filteredText = text.substring(1)
                    noHpEditText.setText(filteredText)
                    noHpEditText.setSelection(noHpEditText.text?.length ?: 0)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Tidak digunakan
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Tidak digunakan
            }
        })

        btnMasuk.setOnClickListener {
            // Membuat instance LoginFragment
            val loginFragment = LoginFragment()

            // Memulai transaksi fragment untuk mengganti fragment
            val transaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.fragment_layout, loginFragment) // Ganti dengan ID container Anda
            transaction.commit()
        }

        btnDaftar.setOnClickListener {
            // Dapatkan data dari input
            val username = view.findViewById<TextInputEditText>(R.id.txt_username).text.toString()
            val namaLengkap = view.findViewById<TextInputEditText>(R.id.txt_name).text.toString()
            val password = view.findViewById<TextInputEditText>(R.id.txt_password).text.toString()
            val repassword = view.findViewById<TextInputEditText>(R.id.txt_repassword).text.toString()
            val noHp = view.findViewById<TextInputEditText>(R.id.txt_nohp).text.toString()
            val email = view.findViewById<TextInputEditText>(R.id.txt_email).text.toString()

            if (password == repassword) {
                if (isPasswordValid(password)) {
                    val register = Register(username, namaLengkap, password, noHp, email)
                    registerUser(register)
                } else {
                    // Tampilkan pesan peringatan pada helperText
                    val passwordLayout = view.findViewById<TextInputLayout>(R.id.textInputLayout4)
                    passwordLayout.helperText = "* Kata sandi harus memiliki setidaknya 8 karakter, mengandung huruf besar, dan mengandung angka."
                }
            } else {
                showErrorToast("Kata sandi tidak sesuai")
            }
        }

        return view
    }

    private fun registerUser(register: Register) {
        val apiService = Service.apiService
        val call = apiService.registerUser(register)

        call.enqueue(object : Callback<ResponseModel> {
            override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                if (response.isSuccessful) {
                    val responseModel = response.body()
                    if (responseModel != null) {
                        // Registrasi berhasil
                        showSuccessToast(responseModel.message)
                    }
                } else {
                    // Registrasi gagal
                    showErrorToast("Registrasi gagal. Cek kembali data Anda.")
                }
            }

            override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                // Error pada koneksi atau server
                showErrorToast("Username sudah digunakan")
//                showErrorToast("$register")
            }
        })
    }

    private fun showSuccessToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun isPasswordValid(password: String): Boolean {
        // Periksa apakah kata sandi memenuhi persyaratan
        val regex = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}\$")

        return regex.matches(password)
    }
}
