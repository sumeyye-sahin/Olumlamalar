package com.sumeyyesahin.olumlamalar

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.databinding.ActivityNefesBinding

class NefesActivity : AppCompatActivity() {
    private lateinit var circleView: CircleView
    private lateinit var btnStart: Button
    private lateinit var tvInstruction: TextView
    private lateinit var tvRoundCounter: TextView
    private lateinit var handler: Handler
    private lateinit var btnend: Button
    private lateinit var binding: ActivityNefesBinding
    private val animators = mutableListOf<ValueAnimator>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNefesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        circleView = findViewById(R.id.circleView)
        btnStart = findViewById(R.id.btnStart)
        tvInstruction = findViewById(R.id.tvInstruction)
        tvRoundCounter = findViewById(R.id.tvRoundCounter)
        btnend = findViewById(R.id.btnend)
        handler = Handler()

        val lottieAnimationView = binding.lt

        btnStart.setOnClickListener {
            circleView.visibility = View.VISIBLE
            tvInstruction.visibility = View.INVISIBLE
            tvRoundCounter.visibility = View.VISIBLE
            btnStart.visibility = View.GONE
            btnend.visibility = View.VISIBLE
            binding.talimat.visibility = View.VISIBLE
            startBreathingExercise(4)
        }
        btnend.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startBreathingExercise(repeatCount: Int) {
        val breatheInDuration = 4000L
        val holdDuration = 7000L
        val breatheOutDuration = 8000L
        val totalCycleDuration = breatheInDuration + holdDuration + breatheOutDuration

        for (i in 0 until repeatCount) {
            val delay = i * totalCycleDuration

            handler.postDelayed({
                // Step 1: Breathe In
                binding.talimat.text = getText(R.string.nefesAl)
                animateCircle(0f, 1f, breatheInDuration, 4, 0)
            }, delay)

            handler.postDelayed({
                // Step 2: Hold
                binding.talimat.text = getText(R.string.nefesTut)
                animateCircle(1f, 0f, holdDuration, 7, 1)
            }, delay + breatheInDuration)

            handler.postDelayed({
                // Step 3: Breathe Out
                binding.talimat.text = getText(R.string.nefesVer)
                animateCircle(0f, 1f, breatheOutDuration, 8, 2)
            }, delay + breatheInDuration + holdDuration)

            // Her tur tamamlandığında tamamlanan tur sayısını güncelle
            handler.postDelayed({
                val completedRounds = i + 1
                tvRoundCounter.text = getString(R.string.nefestext2) + " $completedRounds / $repeatCount"
            }, delay + totalCycleDuration)
        }

        // Döngü tamamlandıktan sonra mesajı göstermek ve aktiviteyi sonlandırmak için gecikme
        handler.postDelayed({
            tvInstruction.text = getString(R.string.breathing_exercise_complete)
            val lottieAnimationView = binding.lt
            lottieAnimationView.setAnimation("nefes.json")
            lottieAnimationView.playAnimation()
            lottieAnimationView.loop(true)
            lottieAnimationView.visibility = View.VISIBLE


            tvInstruction.visibility = View.VISIBLE
            circleView.visibility = View.INVISIBLE
            btnStart.visibility = View.INVISIBLE
            tvRoundCounter.visibility = View.INVISIBLE
            binding.talimat.visibility = View.INVISIBLE
            btnend.visibility = View.VISIBLE

            // 3 saniye sonra aktiviteyi sonlandır
            handler.postDelayed({
                finish()
            }, 5000)
        }, repeatCount * totalCycleDuration)
    }

    private fun animateCircle(startProgress: Float, endProgress: Float, duration: Long, initialSeconds: Int, mode: Int) {
        val animator = ValueAnimator.ofFloat(startProgress, endProgress).apply {
            this.duration = duration
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                circleView.setProgress(progress, mode)

                // Calculate remaining time for countdown
                val remainingTime = if (startProgress < endProgress) {
                    initialSeconds - (initialSeconds * progress).toInt()
                } else {
                    initialSeconds - (initialSeconds * (1 - progress)).toInt()
                }
                circleView.setTimerText(remainingTime.toString())
            }
            start()
        }
        animators.add(animator)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Tüm animasyonları durdur
        animators.forEach { it.cancel() }
        handler.removeCallbacksAndMessages(null)
    }
}
