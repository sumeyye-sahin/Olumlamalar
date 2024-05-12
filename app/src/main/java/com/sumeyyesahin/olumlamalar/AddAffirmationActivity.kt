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
                // Olumlama metni boş değilse yeni bir Olumlamalarlistmodel oluştur
                val newAffirmation = Olumlamalarlistmodel(
                    id = generateUniqueId(), // Benzersiz bir kimlik oluşturmak için bir fonksiyon çağrısı yapılıyor
                    affirmation = olumlamaMetni,
                    category = "Kendi Olumlamalarım",
                    favorite = false
                )

                // Veritabanına yeni olumlamayı ekle
                DBHelper(this).addAffirmation(newAffirmation)
                setResult(RESULT_OK) // Başarı sonucunu ayarla
                finish()


            } else {
                // Olumlama metni boşsa kullanıcıya uyarı ver
                Toast.makeText(this, "Lütfen olumlama metnini girin", Toast.LENGTH_SHORT).show()
            }



        }}

        // Benzersiz bir kimlik oluşturmak için kullanılabilecek bir örnek fonksiyon
        fun generateUniqueId(): Int {
            // Burada rastgele bir benzersiz kimlik oluşturmak için uygun bir yöntem kullanılabilir
            // Örneğin, sistem saatinden milisaniye cinsinden bir zaman damgası alınabilir
            return System.currentTimeMillis().toInt()
        }

    }