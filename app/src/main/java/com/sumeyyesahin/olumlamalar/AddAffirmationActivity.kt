package com.sumeyyesahin.olumlamalar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.sumeyyesahin.olumlamalar.databinding.ActivityAddAffirmationBinding
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel

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
            if (olumlamaMetni.isNotBlank()) {
                // Olumlama metni boş değilse yeni bir olumlama eklemek için DBHelper fonksiyonunu çağır
                DBHelper(this).addNewAffirmation(olumlamaMetni, "Kendi Olumlamalarım")
                setResult(RESULT_OK) // Başarı sonucunu ayarla
                finish()
            } else {
                // Olumlama metni boşsa kullanıcıya uyarı ver
                Toast.makeText(this, "Lütfen olumlama metnini girin", Toast.LENGTH_SHORT).show()
            }
        }
    }
    }