package com.sumeyyesahin.olumlamalar

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NefesActivity : AppCompatActivity() {
    private lateinit var circleView: CircleView
    private lateinit var btnStart: Button
    private lateinit var tvInstruction: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nefes)

        circleView = findViewById(R.id.circleView)
        btnStart = findViewById(R.id.btnStart)
        tvInstruction = findViewById(R.id.tvInstruction)
        imageView = findViewById(R.id.imageView)

        btnStart.setOnClickListener {
            imageView.visibility = View.GONE
            circleView.visibility = View.VISIBLE
            tvInstruction.visibility = View.VISIBLE
            btnStart.visibility = View.GONE
            startBreathingExercise(4)
        }
    }

    private fun startBreathingExercise(repeatCount: Int) {
        val breatheInDuration = 4000L
        val holdDuration = 7000L
        val breatheOutDuration = 8000L
        val totalCycleDuration = breatheInDuration + holdDuration + breatheOutDuration

        for (i in 0 until repeatCount) {
            val delay = i * totalCycleDuration

            Handler().postDelayed({
                // Step 1: Breathe In
                tvInstruction.text = "Nefes Al"
                animateCircle(0f, 1f, breatheInDuration, 4, 0)
            }, delay)

            Handler().postDelayed({
                // Step 2: Hold
                tvInstruction.text = "Nefesini Tut"
                animateCircle(1f, 0f, holdDuration, 7, 1)
            }, delay + breatheInDuration)

            Handler().postDelayed({
                // Step 3: Breathe Out
                tvInstruction.text = "Nefes Ver"
                animateCircle(0f, 1f, breatheOutDuration, 8, 2)
            }, delay + breatheInDuration + holdDuration)
        }
    }

    private fun animateCircle(startProgress: Float, endProgress: Float, duration: Long, initialSeconds: Int, mode: Int) {
        val animator = ValueAnimator.ofFloat(startProgress, endProgress).apply {
            this.duration = duration
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                circleView.setProgress(progress, mode)

                // Calculate remaining time
                val remainingTime = if (startProgress < endProgress) {
                    (initialSeconds * (1 - progress)).toInt()
                } else {
                    (initialSeconds * progress).toInt()
                }
                circleView.setTimerText((initialSeconds - remainingTime).toString())
            }
            start()
        }
    }
}
