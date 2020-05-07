package com.anupam.covid19

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import com.anupam.covid_19.MainActivity
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }

    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activity)
        imageView = findViewById(R.id.imageView)
        val url = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/COVID-19_Outbreak_Cases_in_India.svg/800px-COVID-19_Outbreak_Cases_in_India.svg.png"
        Glide.with(applicationContext).load(url).into(imageView)
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }, SPLASH_TIME_OUT.toLong())
    }


}