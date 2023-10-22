package com.cc.codingcamp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val splashScreenDuration = 2000L // 3000 milliseconds = 3 detik
        Handler().postDelayed({
            // Navigasi ke aktivitas atau fragmen berikutnya
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration)
    }
}