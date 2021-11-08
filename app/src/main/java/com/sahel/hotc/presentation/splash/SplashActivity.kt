package com.sahel.hotc.presentation.splash

import android.content.Intent
import android.content.res.Configuration
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.sahel.hotc.R
import com.sahel.hotc.presentation.home.HomeActivity
import kotlinx.android.synthetic.main.activity_main.*


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WindowInsetsControllerCompat(window, activitySplash).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            controller.isAppearanceLightStatusBars = true
        }
        val video = Uri.parse("android.resource://" + packageName + "/" + R.raw.hotc)
        splashVid.setVideoPath(video.toString())
          
        splashVid.setOnCompletionListener(object:MediaPlayer.OnCompletionListener{
            override fun onCompletion(mp: MediaPlayer?) {
                startNextActivity()
            }

        });
        splashVid.start();

    }



    private fun startNextActivity() {
        if (isFinishing())
            return;
        startActivity( Intent(this, HomeActivity::class.java))
        finish();

    }
}