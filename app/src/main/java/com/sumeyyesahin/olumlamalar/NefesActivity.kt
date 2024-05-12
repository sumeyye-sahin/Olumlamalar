package com.sumeyyesahin.olumlamalar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sumeyyesahin.olumlamalar.databinding.ActivityNefesBinding

class NefesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNefesBinding
    private var seconds = 0
    private var stage = 1  // Nefes almak için 1, tutmak için 2, vermek için 3.
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNefesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        runTimer()

    }
    private fun runTimer() {
        val runnable = object : Runnable {
            override fun run() {
                when (stage) {
                    1 -> {
                        if (seconds > 0) {
                            binding.timerTextView.text = "$seconds"
                            seconds--
                        } else {
                            stage = 2
                            seconds = 7
                        }
                    }
                    2 -> {
                        if (seconds > 0) {
                            binding.timerTextView.text = "$seconds"
                            seconds--
                        } else {
                            stage = 3
                            seconds = 8
                        }
                    }
                    3 -> {
                        if (seconds > 0) {
                            binding.timerTextView.text = "$seconds"
                            seconds--
                        } else {
                            stage = 1
                            seconds = 4  // Süreci tekrar başlat.
                        }
                    }
                }
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }
}