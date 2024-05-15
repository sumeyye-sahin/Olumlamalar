package com.sumeyyesahin.olumlamalar

import android.animation.ValueAnimator
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class NefesActivity : AppCompatActivity() {
    private lateinit var circleView: CircleView
    private lateinit var btnStart: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nefes)

        circleView = findViewById(R.id.circleView)
        btnStart = findViewById(R.id.btnStart)

        btnStart.setOnClickListener {
            startBreathingExercise()
        }
    }

    private fun startBreathingExercise() {
        val breatheInDuration = 4000L
        val holdDuration = 7000L
        val breatheOutDuration = 8000L

        // Step 1: Breathe In
        animateCircle(0f, 1f, breatheInDuration, 4)

        Handler().postDelayed({
            // Step 2: Hold
            animateCircle(1f, 0f, holdDuration, 7)
        }, breatheInDuration)

        Handler().postDelayed({
            // Step 3: Breathe Out
            animateCircle(0f, 1f, breatheOutDuration, 8)
        }, breatheInDuration + holdDuration)
    }

    private fun animateCircle(startProgress: Float, endProgress: Float, duration: Long, initialSeconds: Int) {
        val animator = ValueAnimator.ofFloat(startProgress, endProgress).apply {
            this.duration = duration
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                circleView.setProgress(progress)

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
