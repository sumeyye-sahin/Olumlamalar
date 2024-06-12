package com.sumeyyesahin.olumlamalar

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeyyesahin.olumlamalar.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: CategoryTimeAdapter
    private val categoriesAndTimes = mutableListOf<Pair<String, Pair<Int, Int>>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Spinner verilerini ayarla
        val languages = arrayOf("English", "Türkçe")
        val languageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
        languageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Dil seçimini yükle
        val currentLanguage = sharedPreferences.getString("language", "en")

        // RecyclerView ve Adapter ayarları
        adapter = CategoryTimeAdapter(this, categoriesAndTimes, currentLanguage ?: "en", sharedPreferences)


        // loadCategoriesAndTimes(currentLanguage ?: "en")

        binding.selectLanguageBtn.setOnClickListener {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnEditNotifications.setOnClickListener {
            val intent = Intent(this, IntroUserNotificationSelectActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {

        val currentLanguage = sharedPreferences.getString("language", "en")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("language", currentLanguage)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
    override fun onResume() {
        super.onResume()
        val currentLanguage = sharedPreferences.getString("language", "en")

        adapter.notifyDataSetChanged()
    }
}