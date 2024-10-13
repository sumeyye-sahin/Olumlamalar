package com.sumeyyesahin.olumlamalar.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyesahin.olumlamalar.databinding.ActivityMainBinding
import com.sumeyyesahin.olumlamalar.utils.Constants
import com.sumeyyesahin.olumlamalar.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializeActivity(this)

        viewModel.language.observe(this) { language ->
            viewModel.setLocale(language)
            viewModel.scheduleNotifications(language)
        }

        viewModel.currentIndex.observe(this) { index ->
            binding.imageView2.setImageResource(viewModel.flowerImages[index])
        }

        binding.imageView2.setOnClickListener {
            viewModel.updateFlowerIndex()
        }

        binding.buttonkategori.setOnClickListener {
            Constants.changeButtonBackgroundColor(it)
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        binding.buttonSettings.setOnClickListener {
            Constants.changeButtonBackgroundColor(it)
            startActivity(Intent(this, IntroActivity::class.java))
        }

        binding.buttonnefes.setOnClickListener {
            Constants.changeButtonBackgroundColor(it)
            navigateToBreathActivity()
        }

        viewModel.checkInitialViews(this)
    }

    private fun navigateToBreathActivity() {
        val intent = if (viewModel.isBreathingIntroSeen(this)) {
            Intent(this, BreathActivity::class.java)
        } else {
            Intent(this, BreathingIntroActivity::class.java)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(this)
        binding.imageView2.setImageResource(viewModel.flowerImages[viewModel.currentIndex.value ?: 0])
    }
}
