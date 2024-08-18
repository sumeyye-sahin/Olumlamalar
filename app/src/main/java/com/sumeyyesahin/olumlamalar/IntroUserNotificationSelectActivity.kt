package com.sumeyyesahin.olumlamalar

import android.Manifest
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumeyyesahin.olumlamalar.databinding.ActivityIntroUserNotificationSelectBinding
import java.util.Calendar
import java.util.Locale
import kotlin.random.Random

class IntroUserNotificationSelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroUserNotificationSelectBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val NOTIFICATION_PERMISSION_CODE = 100
    private val categoriesAndTimes = mutableMapOf<String, MutableList<Pair<Int, Int>>>()
    private lateinit var adapter: CategoryTimeAdapter
    private lateinit var dbHelper: DBHelper
    private lateinit var spinnerLanguage: Spinner
    // Soft (Pastel) Renkler Dizisi
    private val softColors = arrayOf(
        Color.parseColor("#FFB3BA"),  // Light Pink
        Color.parseColor("#FFDFBA"),  // Light Peach
        Color.parseColor("#FFFFBA"),  // Light Yellow
        Color.parseColor("#BAFFC9"),  // Light Mint Green
        Color.parseColor("#BAE1FF"),  // Light Sky Blue
        Color.parseColor("#D4A5A5"),  // Light Coral
        Color.parseColor("#C1C1E1"),  // Light Lavender
        Color.parseColor("#A7BED3")   // Soft Blue-Gray
    )

    // Eski renkleri saklamak için bir map
    private val originalButtonColors = mutableMapOf<View, Int>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroUserNotificationSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ActionBar'ı etkinleştirme
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        dbHelper = DBHelper(this)
        requestNotificationPermission()

        val selectedLanguage = intent.getStringExtra("language") ?: sharedPreferences.getString("language", "en")
        setLocale(selectedLanguage ?: "en")

        val categories = resources.getStringArray(R.array.categoriesNotification)
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategories.adapter = categoryAdapter

        binding.recyclerViewCategoryTimes.layoutManager = LinearLayoutManager(this)
        adapter = CategoryTimeAdapter(this, convertMapToList(categoriesAndTimes), selectedLanguage ?: "en", sharedPreferences)
        binding.recyclerViewCategoryTimes.adapter = adapter

        loadCategoriesAndTimes(selectedLanguage ?: "en")

        binding.btnAddTime.setOnClickListener {
            changeButtonBackgroundColor(it)

            val category = binding.spinnerCategories.selectedItem.toString()
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute
            categoriesAndTimes.getOrPut(category) { mutableListOf() }.add(hour to minute)
            adapter.updateItems(convertMapToList(categoriesAndTimes))

            val editor = sharedPreferences.edit()
            editor.putString("categories_and_times", serializeCategoriesAndTimes(categoriesAndTimes))
            editor.putBoolean("notification_setup_done", true)
            editor.apply()

            categoriesAndTimes.forEach { (category, times) ->
                times.forEach { time ->
                    setDailyNotification(time.first, time.second, category)
                }
            }

            saveNotificationSettings(categoriesAndTimes)
        }

        binding.btnSave.setOnClickListener {
            changeButtonBackgroundColor(it)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    showPermissionRationale()
                } else {
                    sharedPreferences.edit().putBoolean("notification_seen", true).apply()
                    navigateToMainActivity()
                    finish() //bu kısma bak
                }
            } else {
                sharedPreferences.edit().putBoolean("notification_seen", true).apply()
                navigateToMainActivity()
                finish() //bu kısma bak
            }
        }

        // Notification Channel Oluşturma
        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeButtonBackgroundColor(button: View) {
        // Rastgele bir soft renk seç
        val randomColor = getRandomSoftColor()

        // Button'un arka planı olarak kullanılan LayerDrawable'ı al
        val background = button.background

        if (background is LayerDrawable) {
            for (i in 0 until background.numberOfLayers) {
                val layer = background.getDrawable(i)
                if (layer is GradientDrawable) {
                    // Eğer button için eski rengi kaydetmediysek, kaydet
                    if (!originalButtonColors.containsKey(button)) {
                        originalButtonColors[button] = (layer.color?.defaultColor ?: Color.TRANSPARENT)
                    }
                    // Rengi değiştir
                    layer.setColor(randomColor)

                    // 1 saniye sonra eski rengi geri yükle
                    Handler(Looper.getMainLooper()).postDelayed({
                        layer.setColor(originalButtonColors[button] ?: randomColor)
                    }, 1000)
                    return
                }
            }
            Log.e("MainActivity", "GradientDrawable bulunamadı.")
        } else {
            Log.e("MainActivity", "LayerDrawable değil: ${background?.javaClass?.name}")
        }
    }

    // Rastgele bir soft renk seçen fonksiyon
    private fun getRandomSoftColor(): Int {
        val randomIndex = Random.nextInt(softColors.size)
        return softColors[randomIndex]
    }

    override fun onResume() {
        super.onResume()

        val currentLanguage = sharedPreferences.getString("language", "en")
        updateCategoriesInSharedPreferences(currentLanguage ?: "en")
        loadCategoriesAndTimes(currentLanguage ?: "en")
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        sharedPreferences.edit().putBoolean("intro_seen", true).apply()

        if (sharedPreferences.getBoolean("intro_seen", false)) {
            val currentLanguage = sharedPreferences.getString("language", "en")
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("language", currentLanguage)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        } else {
            super.onBackPressed()
        }
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
        val requestCode = (category.hashCode() + hour + minute)
        val pendingIntent = PendingIntent.getBroadcast(
            this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        } else {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
    }

    private fun saveNotificationSettings(categoriesAndTimes: Map<String, List<Pair<Int, Int>>>) {
        val editor = sharedPreferences.edit()
        editor.putString("categories_and_times", serializeCategoriesAndTimes(categoriesAndTimes))
        editor.putBoolean("notification_setup_done", true)
        editor.apply()
    }

    private fun loadCategoriesAndTimes(language: String) {
        categoriesAndTimes.clear()
        val serializedData = sharedPreferences.getString("categories_and_times", "")
        if (!serializedData.isNullOrEmpty()) {
            val items = serializedData.split(";")
            for (item in items) {
                val parts = item.split(",")
                if (parts.size == 3) {
                    val category = getLocalizedCategoryName(this, parts[0], language)
                    val hour = parts[1].toInt()
                    val minute = parts[2].toInt()
                    categoriesAndTimes.getOrPut(category) { mutableListOf() }.add(hour to minute)
                }
            }
            adapter.updateItems(convertMapToList(categoriesAndTimes))
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
                Toast.makeText(this, "Bildirim izni verildi", Toast.LENGTH_SHORT).show()
                sharedPreferences.edit().putBoolean("notification_seen", true).apply()
                navigateToMainActivity()
            } else {
                Toast.makeText(this, "Bildirim izni reddedildi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        // Ana sayfaya geri dön
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()  // Bu satır isteğe bağlı; ana aktiviteyi başlattıktan sonra mevcut aktiviteyi bitirir
        return true
    }

    private fun navigateToMainActivity() {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("language", sharedPreferences.getString("language", "en"))
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

    private fun serializeCategoriesAndTimes(data: Map<String, List<Pair<Int, Int>>>): String {
        return data.flatMap { entry ->
            entry.value.map { time ->
                "${entry.key},${time.first},${time.second}"
            }
        }.joinToString(";")
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
    }

    private fun convertMapToList(map: Map<String, List<Pair<Int, Int>>>): MutableList<Pair<String, Pair<Int, Int>>> {
        return map.flatMap { (category, times) ->
            times.map { time ->
                category to time
            }
        }.toMutableList()
    }

    // Bildirim Kanalı Oluşturma
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "daily_notification_channel"
            val channelName = "Daily Notification"
            val channelDescription = "Channel for daily notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
