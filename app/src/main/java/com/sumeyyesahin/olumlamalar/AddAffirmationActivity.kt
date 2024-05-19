package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sumeyyesahin.olumlamalar.databinding.ActivityAddAffirmationBinding
import java.util.Locale

class AddAffirmationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddAffirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAffirmationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.geri.setOnClickListener {
            finish()
        }

        binding.ekle.setOnClickListener {
            // Yeni bir olumlama eklemek için
            val olumlamaMetni = binding.editText.text.toString()
            val language = getUserLanguage(this) // Kullanıcının dilini alın

            if (olumlamaMetni.isNotBlank()) {
                // Olumlama metni boş değilse yeni bir olumlama eklemek için DBHelper fonksiyonunu çağır
                DBHelper(this).addNewAffirmation(olumlamaMetni, getString(R.string.add_affirmation_title), language)
                setResult(RESULT_OK) // Başarı sonucunu ayarla
                finish()
            } else {
                // Olumlama metni boşsa kullanıcıya uyarı ver
                Toast.makeText(this, getString(R.string.toast_empty_affirmation), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getUserLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", null)
        val deviceLanguage = Locale.getDefault().language
        return when {
            language != null -> language
            deviceLanguage == "tr" -> "tr"
            deviceLanguage == "en" -> "en"
            else -> "en"
        }
    }
    fun setUserLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", language).apply()
    }
}
