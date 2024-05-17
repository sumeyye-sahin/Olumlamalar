package com.sumeyyesahin.olumlamalar

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sumeyyesahin.olumlamalar.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val db = DBHelper(this)
        val kategoriListesi = db.getAllCategories()
        val adapter = KategoriAdapter(kategoriListesi)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        //recyclerView.layoutManager = LinearLayoutManager(this) // this, Activity içinde olmalı
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)

    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }


}