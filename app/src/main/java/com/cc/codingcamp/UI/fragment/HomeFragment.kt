package com.cc.codingcamp.UI.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cc.codingcamp.API.Service
import com.cc.codingcamp.UI.activity.CoursedetailActivity
import com.cc.codingcamp.R
import com.cc.codingcamp.adapter.ProductAdapter
import com.cc.codingcamp.modal.Course
import com.cc.codingcamp.modal.User
import com.squareup.picasso.Picasso
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var userprofile: TextView
    private lateinit var fotoProfile: ImageView
    private lateinit var SharedPreferences : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val Userlog = arguments?.getString("username")

        userprofile = view.findViewById(R.id.txt_homeusername)
        fotoProfile = view.findViewById(R.id.txt_homefotoprofil)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        productAdapter = ProductAdapter(requireContext(), emptyList())

        recyclerView.adapter = productAdapter

        productAdapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val selectedCourse = productAdapter.courseList[position]

                val intent = Intent(requireContext(), CoursedetailActivity::class.java)
                intent.putExtra("course_id", selectedCourse.id_modul)
                startActivity(intent)
            }
        })

        loadProductData()
        fecthUserData(Userlog)

        return view
    }

    private fun loadProductData() {
        val apiService = Service.apiService
        apiService.Getproduct().enqueue(object : Callback<List<Course>> {
            override fun onResponse(call: Call<List<Course>>, response: Response<List<Course>>) {
                if (response.isSuccessful) {
                    val courseList = response.body()
                    if (courseList != null) {
                        val limitedCourseList = courseList.take(5)

                        productAdapter.courseList = limitedCourseList
                        productAdapter.notifyDataSetChanged()
                    } else {
                        // Tangani jika data null atau kosong
                    }
                } else {
                    // Tangani kesalahan respons dari API (misalnya respons tidak sukses)
                }
            }
            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
            }
        })
    }

    private fun fecthUserData(Userlog: String?) {
        val apiService = Service.apiService
        apiService.getSession(Userlog).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    if (userList != null && userList.isNotEmpty()) {
                        // Pilih item pertama untuk ditampilkan
                        val user = userList[0]
                        userprofile.text = user.nama_lengkap
                        Picasso.get().load(user.foto_profil).into(fotoProfile)
                    } else {
                        Toast.makeText(requireContext(), "$Userlog", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Tangani kesalahan respons dari API (contohnya, respons tidak berhasil)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Tangani kesalahan koneksi atau kesalahan lainnya
            }
        })
    }
}
