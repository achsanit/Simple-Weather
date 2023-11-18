package com.achsanit.simpleweather.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.achsanit.simpleweather.databinding.ActivitySplashBinding
import com.achsanit.simpleweather.features.home.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        handler.postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, 2000L)
    }
}