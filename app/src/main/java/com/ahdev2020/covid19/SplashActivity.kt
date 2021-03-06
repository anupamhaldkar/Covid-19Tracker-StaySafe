package com.ahdev2020.covid19

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ahdev2020.covid_19.R

class SplashActivity : AppCompatActivity() {
    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide();
        setContentView(R.layout.activity_splash_activity)
        imageView = findViewById(R.id.imageView)
        //val url = "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/COVID-19_Outbreak_Cases_in_India.svg/800px-COVID-19_Outbreak_Cases_in_India.svg.png"
        Glide.with(applicationContext).load("https://i.imgur.com/IqNLMEF.png").error(R.drawable.ic_arrow_downward).into(imageView)
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }
}