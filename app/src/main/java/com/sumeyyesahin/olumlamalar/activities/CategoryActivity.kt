package com.sumeyyesahin.olumlamalar.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sumeyyesahin.olumlamalar.helpers.DBHelper
import com.sumeyyesahin.olumlamalar.adapters.KategoriAdapter
import com.sumeyyesahin.olumlamalar.R
import com.sumeyyesahin.olumlamalar.databinding.ActivityCategoryBinding
import java.util.Locale

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val language = intent.getStringExtra("language") ?: getUserLanguage(this)
        setLocale(language)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val db = DBHelper(this)

        val kategoriListesi = db.getAllCategoriesByLanguage(language)
        val adapter = KategoriAdapter(kategoriListesi, language) // language parametresini geçiriyoruz
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
    fun getUserLanguage(context: Context): String {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", null)
        val deviceLanguage = Locale.getDefault().language
        return when {
            language != null -> language
            deviceLanguage == "tr" -> "tr"
            deviceLanguage == "en" -> "en"
            else -> "en"
        }
    }

    fun setUserLanguage(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("language", language).apply()
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onSupportNavigateUp(): Boolean {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        return true
    }
}
