package com.sumeyyesahin.olumlamalar

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeyyesahin.olumlamalar.databinding.ActivityIntroUserNotificationSelectBinding
import java.util.Calendar
import java.util.Locale

class IntroUserNotificationSelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroUserNotificationSelectBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val NOTIFICATION_PERMISSION_CODE = 100
    private val categoriesAndTimes = mutableListOf<Pair<String, Pair<Int, Int>>>()
    private lateinit var adapter: CategoryTimeAdapter
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroUserNotificationSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DBHelper(this)
        requestNotificationPermission()
        // Seçilen dili uygulayın
        val selectedLanguage = intent.getStringExtra("language") ?: sharedPreferences.getString("language", "en")
        setLocale(selectedLanguage ?: "en")

        // Kategori seçeneklerini Spinner'a ekle
        val categories = resources.getStringArray(R.array.categoriesNotification)
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategories.adapter = categoryAdapter

        binding.recyclerViewCategoryTimes.layoutManager = LinearLayoutManager(this)
        adapter = CategoryTimeAdapter(this, categoriesAndTimes, selectedLanguage ?: "en", sharedPreferences)
        binding.recyclerViewCategoryTimes.adapter = adapter

        loadCategoriesAndTimes(selectedLanguage ?: "en")

        binding.btnAddTime.setOnClickListener {
            val category = binding.spinnerCategories.selectedItem.toString()
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute
            categoriesAndTimes.add(category to (hour to minute))
            adapter.notifyDataSetChanged()
        }

        binding.btnSave.setOnClickListener {
            // Save to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("categories_and_times", serializeCategoriesAndTimes(categoriesAndTimes))
            editor.putBoolean("notification_setup_done", true)
            editor.apply()

            // Set alarms for all categories and times
            for ((category, time) in categoriesAndTimes) {
                setDailyNotification(time.first, time.second, category)
            }

            saveNotificationSettings(categoriesAndTimes)
            // Bildirim izni iste
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    showPermissionRationale()
                } else {
                    // MainActivity'ye geç
                    navigateToMainActivity()
                }
            } else {
                // MainActivity'ye geç
                navigateToMainActivity()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val currentLanguage = sharedPreferences.getString("language", "en")
        updateCategoriesInSharedPreferences(currentLanguage ?: "en")
        loadCategoriesAndTimes(currentLanguage ?: "en")
        adapter.notifyDataSetChanged()
    }
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), NOTIFICATION_PERMISSION_CODE)
            }
        }
    }
    private fun setDailyNotification(hour: Int, minute: Int, category: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("category", category)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, category.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1) // Eğer alarm geçmişte ise, bir gün ekleyin
            }
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun saveNotificationSettings(categoriesAndTimes: List<Pair<String, Pair<Int, Int>>>) {
        val editor = sharedPreferences.edit()
        editor.putString("categories_and_times", serializeCategoriesAndTimes(categoriesAndTimes))
        editor.putBoolean("notification_setup_done", true)
        editor.apply()
    }

    private fun loadCategoriesAndTimes(language: String) {
        categoriesAndTimes.clear() // Önce listeyi temizleyin
        val serializedData = sharedPreferences.getString("categories_and_times", "")
        if (!serializedData.isNullOrEmpty()) {
            val items = serializedData.split(";")
            for (item in items) {
                val parts = item.split(",")
                if (parts.size == 3) {
                    var category = parts[0]
                    val hour = parts[1].toInt()
                    val minute = parts[2].toInt()
                    category = getLocalizedCategoryName(this, category, language)
                    categoriesAndTimes.add(category to (hour to minute))
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun showPermissionRationale() {
        AlertDialog.Builder(this)
            .setTitle("Bildirim İzni Gerekiyor")
            .setMessage("Uygulamanın düzgün çalışabilmesi için bildirim izni vermeniz gerekmektedir.")
            .setPositiveButton("İzin Ver") { dialog, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
                dialog.dismiss()
            }
            .setNegativeButton("Reddet") { dialog, _ ->
                Toast.makeText(this, "Bildirim izni reddedildi", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                // İzin verildi
                Toast.makeText(this, "Bildirim izni verildi", Toast.LENGTH_SHORT).show()
                // MainActivity'ye geç
                navigateToMainActivity()
            } else {
                // İzin reddedildi
                Toast.makeText(this, "Bildirim izni reddedildi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToMainActivity() {

        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val introSeen = sharedPreferences.getBoolean("intro_seen", false)
        val intent = if (introSeen) {
            Intent(this, SettingsActivity::class.java)
        } else{
            sharedPreferences.edit().putBoolean("intro_seen", true).apply()
        Intent(this, IntroUserNotificationSelectActivity::class.java)
        intent.putExtra("language", sharedPreferences.getString("language", "en"))
        }
        startActivity(intent)
        finish()
}

private fun setLocale(languageCode: String) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
}

private fun serializeCategoriesAndTimes(data: List<Pair<String, Pair<Int, Int>>>): String {
    return data.joinToString(";") { "${it.first},${it.second.first},${it.second.second}" }
}

private fun updateCategoriesInSharedPreferences(language: String) {
    val editor = sharedPreferences.edit()
    val serializedData = sharedPreferences.getString("categories_and_times", "")
    if (!serializedData.isNullOrEmpty()) {
        val updatedItems = serializedData.split(";").map { item ->
            val parts = item.split(",")
            if (parts.size == 3) {
                val category = parts[0]
                val hour = parts[1].toInt()
                val minute = parts[2].toInt()
                val updatedCategory = getLocalizedCategoryName(this, category, language)
                "$updatedCategory,$hour,$minute"
            } else {
                item
            }
        }.joinToString(";")
        editor.putString("categories_and_times", updatedItems)
    }
    editor.apply()
}

private fun getLocalizedCategoryName(context: Context, category: String, language: String): String {
    val resources = when (language) {
        "tr" -> LocaleHelper.setLocale(context, "tr").resources
        "en" -> LocaleHelper.setLocale(context, "en").resources
        else -> LocaleHelper.setLocale(context, "en").resources
    }
    return when (language) {
        "tr" -> when (category) {
            "General Affirmations" -> resources.getString(R.string.general_affirmations)
            "Body Affirmations" -> resources.getString(R.string.body_affirmations)
            "Faith Affirmations" -> resources.getString(R.string.faith_affirmations)
            "Tough Days Affirmations" -> resources.getString(R.string.bad_days_affirmations)
            "Love Affirmations" -> resources.getString(R.string.love_affirmations)
            "Self-Worth Affirmations" -> resources.getString(R.string.self_value_affirmations)
            "Stress and Anxiety Affirmations" -> resources.getString(R.string.stress_affirmations)
            "Positive Thought Affirmations" -> resources.getString(R.string.positive_thought_affirmations)
            "Success Affirmations" -> resources.getString(R.string.success_affirmations)
            "Personal Development Affirmations" -> resources.getString(R.string.personal_development_affirmations)
            "Time Management Affirmations" -> resources.getString(R.string.time_management_affirmations)
            "Relationship Affirmations" -> resources.getString(R.string.relationship_affirmations)
            "Prayer and Request" -> resources.getString(R.string.prayer_affirmations)
            else -> category
        }
        "en" -> when (category) {
            "Genel Olumlamalar" -> resources.getString(R.string.general_affirmations)
            "Beden Olumlamaları" -> resources.getString(R.string.body_affirmations)
            "İnanç Olumlamaları" -> resources.getString(R.string.faith_affirmations)
            "Zor Günler Olumlamaları" -> resources.getString(R.string.bad_days_affirmations)
            "Sevgi ve Aşk Olumlamaları" -> resources.getString(R.string.love_affirmations)
            "Öz Değer Olumlamaları" -> resources.getString(R.string.self_value_affirmations)
            "Stres ve Kaygı Olumlamaları" -> resources.getString(R.string.stress_affirmations)
            "Pozitif Düşünce Olumlamaları" -> resources.getString(R.string.positive_thought_affirmations)
            "Başarı Olumlamaları" -> resources.getString(R.string.success_affirmations)
            "Kişisel Gelişim Olumlamaları" -> resources.getString(R.string.personal_development_affirmations)
            "Zaman Yönetimi Olumlamaları" -> resources.getString(R.string.time_management_affirmations)
            "İlişki Olumlamaları" -> resources.getString(R.string.relationship_affirmations)
            "Dua ve İstek" -> resources.getString(R.string.prayer_affirmations)
            else -> category
        }
        else -> category
    }
}}
