package com.cc.codingcamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.cc.codingcamp.fragment.CourseFragment
import com.cc.codingcamp.fragment.EventFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.cc.codingcamp.fragment.HomeFragment


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val dashboardFragment = HomeFragment()
    private val eventFragment = EventFragment()
    private val courseFragment = CourseFragment()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_dashboard -> {
                replaceFragment(dashboardFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_event -> {
                replaceFragment(eventFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_course -> {
                replaceFragment(courseFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            replaceFragment(dashboardFragment)
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .commit()
    }
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()  // Kembali dari fragment lain ke dashboard
        } else {
            super.onBackPressed()  // Keluar dari aplikasi jika sudah di fragment dashboard
        }
    }
}