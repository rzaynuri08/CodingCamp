package com.cc.codingcamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.cc.codingcamp.UI.fragment.CourseFragment
import com.cc.codingcamp.UI.fragment.EventFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.cc.codingcamp.UI.fragment.HomeFragment

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val dashboardFragment = HomeFragment()
    private val eventFragment = EventFragment()
    private val courseFragment = CourseFragment()

    private var doubleBackToExitPressedOnce = false

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_dashboard -> {
                val Userlog = intent.getStringExtra("username")
                val bundle = Bundle()
                bundle.putString("username", Userlog)
                dashboardFragment.arguments = bundle
                replaceFragment(dashboardFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_event -> {
                replaceFragment(eventFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_course -> {
                val Userlog = intent.getStringExtra("username")
                val bundle = Bundle()
                bundle.putString("username", Userlog)
                courseFragment.arguments = bundle  // Corrected this line
                replaceFragment(courseFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Userlog = intent.getStringExtra("username")
        val bundle = Bundle()
        bundle.putString("username", Userlog)
        dashboardFragment.arguments = bundle

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        if (savedInstanceState == null) {
            replaceFragment(dashboardFragment)
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Klik tombol kembali lagi untuk keluar", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)
    }
}