package com.sumeyyesahin.olumlamalar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sumeyyesahin.olumlamalar.databinding.ActivityFavoriesBinding
class FavoriesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding
        val binding = ActivityFavoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)




    }



}