package com.anupam.covid19

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {
    companion object {
        private const val SPLASH_TIME_OUT = 2000
    }

    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_activity)
        imageView = findViewById(R.id.imageView)

        Glide.with(applicationContext).load("https://i.imgur.com/IqNLMEF.png").error(R.drawable.ic_arrow_downward)
            .into(imageView)
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
        }, SPLASH_TIME_OUT.toLong())
    }


}