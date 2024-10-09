package com.sumeyyesahin.olumlamalar.activities

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityNefesBinding
import com.sumeyyesahin.olumlamalar.views.CircleView
import kotlin.random.Random

class NefesActivity : AppCompatActivity() {
    private lateinit var circleView: CircleView
    private lateinit var btnStart: Button
    private lateinit var tvInstruction: TextView
    private lateinit var tvRoundCounter: TextView
    private lateinit var handler: Handler
    private lateinit var btnend: Button
    private lateinit var binding: ActivityNefesBinding
    private val animators = mutableListOf<ValueAnimator>()
    // Soft (Pastel) Renkler Dizisi
    private val softColors = arrayOf(
        Color.parseColor("#FFB3BA"),  // Light Pink
        Color.parseColor("#FFDFBA"),  // Light Peach
        Color.parseColor("#FFFFBA"),  // Light Yellow
        Color.parseColor("#BAFFC9"),  // Light Mint Green
        Color.parseColor("#BAE1FF"),  // Light Sky Blue
        Color.parseColor("#D4A5A5"),  // Light Coral
        Color.parseColor("#C1C1E1"),  // Light Lavender
        Color.parseColor("#A7BED3")   // Soft Blue-Gray
    )

    // Eski renkleri saklamak için bir map
    private val originalButtonColors = mutableMapOf<View, Int>()
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNefesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.nefestext)


        circleView = findViewById(R.id.circleView)
        btnStart = findViewById(R.id.btnStart)
        tvInstruction = findViewById(R.id.tvInstruction)
        tvRoundCounter = findViewById(R.id.tvRoundCounter)
        btnend = findViewById(R.id.btnend)
        handler = Handler()

        val lottieAnimationView = binding.lt

        btnStart.setOnClickListener {
            changeButtonBackgroundColor(it)

            circleView.visibility = View.VISIBLE
            tvInstruction.visibility = View.INVISIBLE
            tvRoundCounter.visibility = View.VISIBLE
            btnStart.visibility = View.GONE
            btnend.visibility = View.VISIBLE
            binding.talimat.visibility = View.VISIBLE
            startBreathingExercise(4)
        }
        btnend.setOnClickListener {
            changeButtonBackgroundColor(it)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
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
                tvRoundCounter.text = getString(R.string.nefestext2) + " $completedRounds / $repeatCount"
            }, delay + totalCycleDuration)
        }


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
                circleView.setProgress(progress, mode)

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
    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeButtonBackgroundColor(button: View) {

        val randomColor = getRandomSoftColor()

        val background = button.background

        if (background is LayerDrawable) {
            for (i in 0 until background.numberOfLayers) {
                val layer = background.getDrawable(i)
                if (layer is GradientDrawable) {

                    if (!originalButtonColors.containsKey(button)) {
                        originalButtonColors[button] = (layer.color?.defaultColor ?: Color.TRANSPARENT)
                    }

                    layer.setColor(randomColor)

                    Handler(Looper.getMainLooper()).postDelayed({
                        layer.setColor(originalButtonColors[button] ?: randomColor)
                    }, 1000)
                    return
                }
            }
            Log.e("MainActivity", "GradientDrawable bulunamadı.")
        } else {
            Log.e("MainActivity", "LayerDrawable değil: ${background?.javaClass?.name}")
        }
    }

    private fun getRandomSoftColor(): Int {
        val randomIndex = Random.nextInt(softColors.size)
        return softColors[randomIndex]
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
