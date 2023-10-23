package com.cc.codingcamp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cc.codingcamp.fragment.LoginFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (savedInstanceState == null) {
            val loginFragment = LoginFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, loginFragment)
                .commit()
        }
    }
}
