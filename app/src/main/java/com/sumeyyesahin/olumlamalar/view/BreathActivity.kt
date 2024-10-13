package com.sumeyyesahin.olumlamalar.view

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityNefesBinding
import com.sumeyyesahin.olumlamalar.utils.Constants

class BreathActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var btnend: Button
    private lateinit var binding: ActivityNefesBinding
    private val animators = mutableListOf<ValueAnimator>()



    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNefesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.nefestext)


        btnend = findViewById(R.id.btnend)
        handler = Handler()

        val lottieAnimationView = binding.lt

        binding.btnStart.setOnClickListener {
            Constants.changeButtonBackgroundColor(it)

            binding.circleView.visibility = View.VISIBLE
            binding.tvInstruction.visibility = View.INVISIBLE
            binding.tvRoundCounter.visibility = View.VISIBLE
            binding.btnStart.visibility = View.GONE
            btnend.visibility = View.VISIBLE
            binding.talimat.visibility = View.VISIBLE
            startBreathingExercise(4)
        }
        btnend.setOnClickListener {
            Constants.changeButtonBackgroundColor(it)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun startBreathingExercise(repeatCount: Int) {
        val breatheInDuration = 4000L
        val holdDuration = 7000L
        val breatheOutDuration = 8000L
        val totalCycleDuration = breatheInDuration + holdDuration + breatheOutDuration

        for (i in 0 until repeatCount) {
            val delay = i * totalCycleDuration

            handler.postDelayed({
                binding.talimat.text = getText(R.string.nefesAl)
                animateCircle(0f, 1f, breatheInDuration, 4, 0)
            }, delay)

            handler.postDelayed({
                binding.talimat.text = getText(R.string.nefesTut)
                animateCircle(1f, 0f, holdDuration, 7, 1)
            }, delay + breatheInDuration)

            handler.postDelayed({
                binding.talimat.text = getText(R.string.nefesVer)
                animateCircle(0f, 1f, breatheOutDuration, 8, 2)
            }, delay + breatheInDuration + holdDuration)

            handler.postDelayed({
                val completedRounds = i + 1
                binding.tvRoundCounter.text = getString(R.string.nefestext2) + " $completedRounds / $repeatCount"
            }, delay + totalCycleDuration)
        }


        handler.postDelayed({
            binding.tvInstruction.text = getString(R.string.breathing_exercise_complete)
            val lottieAnimationView = binding.lt
            lottieAnimationView.setAnimation("nefes.json")
            lottieAnimationView.playAnimation()
            lottieAnimationView.loop(true)
            lottieAnimationView.visibility = View.VISIBLE


            binding.tvInstruction.visibility = View.VISIBLE
            binding.circleView.visibility = View.INVISIBLE
            binding.btnStart.visibility = View.INVISIBLE
            binding.tvRoundCounter.visibility = View.INVISIBLE
            binding.talimat.visibility = View.INVISIBLE
            btnend.visibility = View.VISIBLE

            handler.postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }, 5000)
        }, repeatCount * totalCycleDuration)


    }

    private fun animateCircle(startProgress: Float, endProgress: Float, duration: Long, initialSeconds: Int, mode: Int) {
        val animator = ValueAnimator.ofFloat(startProgress, endProgress).apply {
            this.duration = duration
            addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                binding.circleView.setProgress(progress, mode)

                val remainingTime = if (startProgress < endProgress) {
                    initialSeconds - (initialSeconds * progress).toInt()
                } else {
                    initialSeconds - (initialSeconds * (1 - progress)).toInt()
                }
                binding.circleView.setTimerText(remainingTime.toString())
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

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
