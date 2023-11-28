package com.achsanit.simpleweather.features.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.achsanit.simpleweather.R
import com.achsanit.simpleweather.databinding.ActivitySplashBinding
import com.achsanit.simpleweather.features.home.ui.MainActivity
import com.achsanit.simpleweather.foundation.utils.isDarkTheme

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setStatusBarBackground()

        handler.postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000L)
    }

    private fun setStatusBarBackground() {
        if (isDarkTheme()) {
            window.statusBarColor =
                ContextCompat.getColor(this, R.color.md_theme_dark_background)
        } else {
            window.statusBarColor =
                ContextCompat.getColor(this, R.color.md_theme_light_background)
        }
    }
}