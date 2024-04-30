package com.sumeyyesahin.olumlamalar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        
        
        
        // içeriği başka uygulamalar ile paylaşmayı sağlayan kod
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Bu bir örnek metin")
        // Paylaşım menüsünü başlatın
        startActivity(Intent.createChooser(shareIntent, "İçeriği Paylaş"))

    }
}