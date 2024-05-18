package com.sumeyyesahin.olumlamalar

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumeyyesahin.olumlamalar.databinding.ActivityFavoriesBinding
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel

class FavoriesActivity : AppCompatActivity() {
    private lateinit var favoritesAdapter: FavoriAdapter
    private lateinit var favoriteAffirmations: List<Olumlamalarlistmodel>
    private lateinit var binding: ActivityFavoriesBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Favori olumlamaları veritabanından al
        favoriteAffirmations = DBHelper(this).getFavoriteAffirmations().distinctBy{ it.affirmation }

        // RecyclerView ve Adapter'ı bağla

        if (favoriteAffirmations.isEmpty()) {
            binding.textViewFav.visibility = View.VISIBLE
            binding.recyclerViewFav.visibility = View.GONE
            binding.baslik.visibility = View.GONE
            binding.topimage.visibility = View.GONE
        } else {
            // Favori olumlamaları göster
            binding.textViewFav.visibility = View.GONE
            binding.recyclerViewFav.visibility = View.VISIBLE
            binding.topimage.visibility = View.VISIBLE
            binding.baslik.visibility = View.VISIBLE
            val recyclerView: RecyclerView = binding.recyclerViewFav
            favoritesAdapter = FavoriAdapter(favoriteAffirmations)
            recyclerView.adapter = favoritesAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
    }}


}