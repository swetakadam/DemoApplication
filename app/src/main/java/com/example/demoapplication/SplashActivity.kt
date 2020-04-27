package com.example.demoapplication

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.activity_splash.*
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splash_animation.addAnimatorListener(object : Animator.AnimatorListener {
            override
            fun onAnimationStart(animation: Animator) {
                Timber.d("Animation:", "start")
            }

            override
            fun onAnimationEnd(animation: Animator) {
                Timber.d("Animation:", "end")
                //findNavController().navigate(R.id.action_splashFragment_to_nav_home)

                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                //go to next [age

            }

            override
            fun onAnimationCancel(animation: Animator) {
                Timber.d("Animation:", "cancel")
            }

            override
            fun onAnimationRepeat(animation: Animator) {
                Timber.d("Animation:", "repeat")
            }
        })
    }
}
