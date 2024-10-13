package com.sumeyyesahin.olumlamalar.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeyyesahin.olumlamalar.listener.OnFavoriteDeleteListener
import com.sumeyyesahin.olumlamalar.adapters.FavoriteAdapter
import com.sumeyyesahin.olumlamalar.databinding.ActivityFavoriesBinding
import com.sumeyyesahin.olumlamalar.model.AffirmationsListModel
import com.sumeyyesahin.olumlamalar.utils.GetSetUserLanguage.getUserLanguage
import com.sumeyyesahin.olumlamalar.viewmodel.FavoriteViewModel

class FavoritesActivity : AppCompatActivity() {
    private lateinit var favoritesAdapter: FavoriteAdapter
    private lateinit var favoriteAffirmations: List<AffirmationsListModel>
    private lateinit var binding: ActivityFavoriesBinding
    private val favoritesViewModel: FavoriteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val language = getUserLanguage(this)

        favoritesViewModel.loadFavoriteList(language)

        favoritesAdapter = FavoriteAdapter(emptyList(), language, object :
            OnFavoriteDeleteListener {
            override fun onDeleteFavorite(affirmation: AffirmationsListModel) {
                favoritesViewModel.deleteFavorite(affirmation)
            }
        })

        binding.recyclerViewFav.apply {
            adapter = favoritesAdapter
            layoutManager = LinearLayoutManager(this@FavoritesActivity)
        }

        favoritesViewModel.favoriteList.observe(this) { affirmations ->
            if (affirmations.isEmpty()) {
                showEmptyState()
            } else {
                hideEmptyState()
                favoritesAdapter.updateList(affirmations)
            }
        }
    }

    private fun showEmptyState() {
        binding.textViewFav.visibility = View.VISIBLE
        binding.recyclerViewFav.visibility = View.GONE
        binding.baslik.visibility = View.GONE
        binding.topimage.visibility = View.GONE
    }

    private fun hideEmptyState() {
        binding.textViewFav.visibility = View.GONE
        binding.recyclerViewFav.visibility = View.VISIBLE
        binding.baslik.visibility = View.VISIBLE
        binding.topimage.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
