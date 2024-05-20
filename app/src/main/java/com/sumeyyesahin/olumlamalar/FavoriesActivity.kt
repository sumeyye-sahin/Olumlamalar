package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sumeyyesahin.olumlamalar.databinding.ActivityFavoriesBinding
import com.sumeyyesahin.olumlamalar.model.Olumlamalarlistmodel
import java.util.Locale

class FavoriesActivity : AppCompatActivity() {
    private lateinit var favoritesAdapter: FavoriAdapter
    private lateinit var favoriteAffirmations: List<Olumlamalarlistmodel>
    private lateinit var binding: ActivityFavoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val language = getUserLanguage(this)

        // Favori olumlamaları veritabanından al
        favoriteAffirmations = DBHelper(this).getFavoriteAffirmationsByLanguage(language).distinctBy { it.affirmation }

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
            favoritesAdapter = FavoriAdapter(favoriteAffirmations, language)
            recyclerView.adapter = favoritesAdapter
            recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    fun getUserLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("language", Locale.getDefault().language) ?: "en" // Varsayılan olarak İngilizce
    }

    fun setUserLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", language).apply()
    }
}
