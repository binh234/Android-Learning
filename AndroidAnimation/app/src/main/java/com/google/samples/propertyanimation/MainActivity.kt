/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.propertyanimation

import android.animation.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import kotlin.concurrent.schedule
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    lateinit var star: ImageView
    lateinit var rotateButton: Button
    lateinit var translateButton: Button
    lateinit var scaleButton: Button
    lateinit var fadeButton: Button
    lateinit var colorizeButton: Button
    lateinit var showerButton: Button
    private var onShowerClick = false

    private lateinit var timer: CountDownTimer
    private val COUNTDOWN_TIME = 30000L
    private val ELAPSE_TIME = 100L
    private var _currentTime = MutableLiveData<Long>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        star = findViewById(R.id.star)
        rotateButton = findViewById<Button>(R.id.rotateButton)
        translateButton = findViewById<Button>(R.id.translateButton)
        scaleButton = findViewById<Button>(R.id.scaleButton)
        fadeButton = findViewById<Button>(R.id.fadeButton)
        colorizeButton = findViewById<Button>(R.id.colorizeButton)
        showerButton = findViewById<Button>(R.id.showerButton)

        rotateButton.setOnClickListener {
            rotater()
        }

        translateButton.setOnClickListener {
            translater()
        }

        scaleButton.setOnClickListener {
            scaler()
        }

        fadeButton.setOnClickListener {
            fader()
        }

        colorizeButton.setOnClickListener {
            colorizer()
        }

        showerButton.setOnClickListener {
            shower()
        }

        _currentTime.observe(this, androidx.lifecycle.Observer {
            createStar()
        })
    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(star, View.ROTATION, -360f, 0f)
        animator.setupAnimator(rotateButton)
        animator.start()
    }

    private fun translater() {
        val forwardAnimator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X,0f, 200f)
        forwardAnimator.setupAnimator(translateButton)
        forwardAnimator.repeatCount = 1
        forwardAnimator.repeatMode = ObjectAnimator.REVERSE
        val backwardAnimator = ObjectAnimator.ofFloat(star, View.TRANSLATION_X, 0f, -200f)
        backwardAnimator.setupAnimator(translateButton)
        backwardAnimator.repeatCount = 1
        backwardAnimator.repeatMode = ObjectAnimator.REVERSE

        val set = AnimatorSet()
        set.playSequentially(forwardAnimator, backwardAnimator)
        set.start()
    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(star, scaleX, scaleY)
        animator.setupAnimator(scaleButton)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun fader() {
        val animator = ObjectAnimator.ofFloat(star, View.ALPHA, 0f)
        animator.setupAnimator(fadeButton)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun colorizer() {
        val animator = ObjectAnimator.ofArgb(star, "backgroundColor", Color.BLACK, Color.RED)
        animator.setupAnimator(colorizeButton)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    private fun shower() {
        onShowerClick = !onShowerClick
        var i = 10
        if (onShowerClick) {
            star.visibility = View.GONE
            resetTimer()
        }
        else {
            star.visibility = View.VISIBLE
            timer.cancel()
            _currentTime.value = 0L
        }
    }

    private fun ObjectAnimator.setupAnimator(view: View) {
        duration = 1000
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                view.isEnabled = true
            }
        })
    }

    private fun createStar() {
        val container = star.parent as ViewGroup
        val containerW = container.width
        val containerH = container.height

        var starW = star.width.toFloat()
        var starH = star.height.toFloat()

        val newStar = AppCompatImageView(this)
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        container.addView(newStar)

        newStar.scaleX = Math.random().toFloat() * 2f + .1f
        newStar.scaleY = newStar.scaleX
        newStar.translationX = Math.random().toFloat() * containerW - starW / 2
        starW *= newStar.scaleX
        starH *= newStar.scaleY

        val mover =
            ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y, -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)
        val rotator =
            ObjectAnimator.ofFloat(newStar, View.ROTATION, (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()

        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                container.removeView(newStar)
            }
        })
        set.start()
    }

    private fun resetTimer() {
        timer = object : CountDownTimer(COUNTDOWN_TIME, ELAPSE_TIME) {
            override fun onFinish() {
                _currentTime.value = 0L
                star.visibility = View.VISIBLE
            }

            override fun onTick(p0: Long) {
                _currentTime.value = p0
            }

        }
        timer.start()
    }
}
