package com.sumeyyesahin.olumlamalar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sumeyyesahin.olumlamalar.databinding.ActivityMainBinding
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonnefes.setOnClickListener {
            val intent = Intent(this, NefesActivity::class.java)
            startActivity(intent)
        }



        /*

          // içeriği başka uygulamalar ile paylaşmayı sağlayan kod
          val shareIntent = Intent(Intent.ACTION_SEND)
          shareIntent.type = "text/plain"
          shareIntent.putExtra(Intent.EXTRA_TEXT, "Bu bir örnek metin")
          // Paylaşım menüsünü başlatın
          startActivity(Intent.createChooser(shareIntent, "İçeriği Paylaş"))
  */
    }




    fun kategorileregit(view: View) {
        val intent = Intent(view.context, CategoryActivity::class.java)
        view.context.startActivity(intent)
    }

}