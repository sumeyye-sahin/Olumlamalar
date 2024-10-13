package com.sumeyyesahin.olumlamalar.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityAddAffirmationBinding
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage

class AddAffirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAffirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAffirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding.geri.setOnClickListener {
            binding.geri.alpha = 0.5f
            binding.geri.postDelayed({
                binding.geri.alpha = 1f
            }, 300)
            finish()
        }

        binding.ekle.setOnClickListener {

            binding.ekle.alpha = 0.5f
            binding.ekle.postDelayed({
                binding.ekle.alpha = 1f
            }, 300)
            val olumlamaMetni = binding.editText.text.toString()
            val language = GetSetUserLanguage.getUserLanguage(this) // Kullanıcı dili alınır

            if (olumlamaMetni.isNotBlank()) {

                // Olumlama metni boş değilse yeni bir olumlama eklemek için DBHelper fonksiyonu çağrılır
                DBHelper(this).addNewAffirmation(olumlamaMetni, getString(R.string.add_affirmation_title), language)
                setResult(RESULT_OK) // Başarı sonucu
                finish()
            } else {
                Toast.makeText(this, getString(R.string.toast_empty_affirmation), Toast.LENGTH_SHORT).show()
            }
        }
    }

}
