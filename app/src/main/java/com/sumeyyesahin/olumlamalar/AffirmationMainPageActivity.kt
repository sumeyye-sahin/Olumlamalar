package com.sumeyyesahin.olumlamalar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sumeyyesahin.olumlamalar.databinding.ActivityAffirmationMainPageBinding

class AffirmationMainPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAffirmationMainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun kategori(view: View){
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    fun share(view: View){
        // içeriği başka uygulamalar ile paylaşmayı sağlayan kod
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Bu bir örnek metin")
        // Paylaşım menüsünü başlatın
        startActivity(Intent.createChooser(shareIntent, "İçeriği Paylaş"))
    }


}