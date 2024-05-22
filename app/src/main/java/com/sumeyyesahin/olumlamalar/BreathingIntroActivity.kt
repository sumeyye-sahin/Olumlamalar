package com.sumeyyesahin.olumlamalar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.databinding.ActivityBreathingIntroBinding

class BreathingIntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBreathingIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBreathingIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val btnContinue = findViewById<Button>(R.id.btnContinue)
        btnContinue.setOnClickListener {
            // Kullanıcı nefes egzersizi tanıtım sayfasını gördü, SharedPreferences'e kaydet
            val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("breathing_intro_seen", true).apply()

            // Nefes egzersizi aktivitesine geç
            val intent = Intent(this, NefesActivity::class.java)
            startActivity(intent)
            finish()

            btnContinue.visibility = View.GONE
            binding.btnnext.visibility = View.VISIBLE
        }

        binding.btnnext.setOnClickListener {
            binding.tvBreathingIntro.text = "4-7-8 nefes tekniğine başlamadan önce nasıl uygulandığına bakalım.\n \nAdımlar:\n" +
                    "1-Hazırlık: Rahat bir pozisyonda oturun veya uzanın, dilinizi üst dişlerinizin arkasına yerleştirin.\n" +
                    "2-Nefes Alma: Burnunuzdan 4 saniye boyunca sessizce nefes alın.\n" +
                    "3-Nefes Tutma: Nefesinizi 7 saniye boyunca tutun.\n" +
                    "4-Nefes Verme: Ağızdan 8 saniye boyunca sesli bir şekilde nefes verin.\n" +
                    "\n" +
                    "Bu adımlar 4 defa tekralanmalı.  \n" +
                    "Günde iki kez (sabah ve akşam) pratik yapmak iyi bir başlangıç olabilir."

            binding.basliknfs.text = "4-7-8 Nefes Tekniği"
            binding.btnnext.visibility = View.GONE
            binding.btnContinue.visibility = View.VISIBLE
        }
    }
}

