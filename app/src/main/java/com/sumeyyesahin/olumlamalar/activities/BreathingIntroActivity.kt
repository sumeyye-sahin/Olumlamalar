package com.sumeyyesahin.olumlamalar.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityBreathingIntroBinding

class BreathingIntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBreathingIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreathingIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        btnContinue.setOnClickListener {

            val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("breathing_intro_seen", true).apply()


            val intent = Intent(this, NefesActivity::class.java)
            startActivity(intent)
            finish()

            btnContinue.visibility = View.GONE
            binding.btnnext.visibility = View.VISIBLE
        }

        binding.btnnext.setOnClickListener {
            binding.tvBreathingIntro.text = getString(R.string.nefes2)

            binding.basliknfs.text = getString(R.string.nefestext)
            binding.btnnext.visibility = View.GONE
            binding.btnContinue.visibility = View.VISIBLE
        }


    }

    override fun onSupportNavigateUp(): Boolean {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

