package com.cc.codingcamp.UI.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.cc.codingcamp.R
import com.cc.codingcamp.UI.fragment.DetailproductFragment
import com.cc.codingcamp.UI.fragment.ModulBabFragment
import com.cc.codingcamp.UI.fragment.QuizFragment

@Suppress("DEPRECATION")
class ModuldetailActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moduldetail)

        bottomNavigationView = findViewById(R.id.NavModul)

        val courseId = intent.getStringExtra("course_id")

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.modul -> {
                    val bundle = Bundle()
                    bundle.putString("idJenisModul", courseId)
                    val modulBabFragment = ModulBabFragment() // Rename variable to start with lowercase
                    modulBabFragment.arguments = bundle

                    replaceFragment(modulBabFragment) // Use the variable here
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.quiz -> {
                    replaceFragment(QuizFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        bottomNavigationView.selectedItemId = R.id.modul
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}