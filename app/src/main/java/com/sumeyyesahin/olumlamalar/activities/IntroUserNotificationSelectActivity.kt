package com.sumeyyesahin.olumlamalar.activities

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sumeyyesahin.olumlamalar.services.NotificationWorker
import com.sumeyyesahin.olumlamalar.R
import java.util.Calendar
import java.util.concurrent.TimeUnit

class IntroUserNotificationSelectActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var progressBar: ProgressBar
    private lateinit var spinnerCategories: Spinner
    private lateinit var addButton: Button
    private lateinit var saveButton: Button
    private lateinit var alarmsContainer: LinearLayout
    private var selectedHour: Int = -1
    private var selectedMinute: Int = -1
    private var alarmCount: Int = 0
    private lateinit var categoryAdapter: ArrayAdapter<String>
    private val categoriesList = mutableListOf<String>()
    private lateinit var timeButton: Button

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_user_notification_select)

        // SharedPreferences'i başlat
        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        progressBar = findViewById(R.id.progressBar)
        spinnerCategories = findViewById(R.id.spinnerCategories)
        addButton = findViewById(R.id.btnAddTime)
        saveButton = findViewById(R.id.btnSave)
        alarmsContainer = findViewById(R.id.alarmsContainer)

        // Dil değişikliğini kontrol et ve gerekirse alarmları iptal et
        checkLanguageChange()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        // Spinner için ArrayAdapter oluşturma
        categoriesList.addAll(resources.getStringArray(R.array.categoriesNotification))
        categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriesList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategories.adapter = categoryAdapter

        // Saat seçimi için butona tıklama
        findViewById<Button>(R.id.timeButton).setOnClickListener {
            showTimePickerDialog()
        }

        // Ekle butonuna tıklama
        addButton.setOnClickListener {
            addTime()
        }

        // Kaydet butonuna tıklama
        saveButton.setOnClickListener {
            saveSettings()
        }
        timeButton = findViewById(R.id.timeButton)

        // Saat seçimi için butona tıklama
        timeButton.setOnClickListener {
            showTimePickerDialog()
        }
        // Uygulama açılırken mevcut alarmları yükle
        showProgressBar()
        loadAlarms()
    }

    private fun checkLanguageChange() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lastCheckTimestamp = sharedPreferences.getLong("last_checked_language_timestamp", 0L) // Son kontrol zamanı
        val lastLanguageChangeTimestamp = sharedPreferences.getLong("language_change_timestamp", 0L) // Son dil değişiklik zamanı

        // Eğer dil değişiklik zaman damgası son kontrol zamanından daha yeniyse, dil değişmiş demektir
        if (lastLanguageChangeTimestamp > lastCheckTimestamp) {
            cancelAllAlarms()
            sharedPreferences.edit().putLong("last_checked_language_timestamp", System.currentTimeMillis()).apply() // Şu anki zamanı kaydet
            Toast.makeText(this, "Dil değişimi sonrası bildirimleri güncellemelisin!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelAllAlarms() {
        // WorkManager'daki tüm işleri iptal et
        WorkManager.getInstance(this).cancelAllWork()

        // Alarmları kaldır ve arayüzü temizle
        alarmsContainer.removeAllViews()
        alarmCount = 0

        // SharedPreferences'taki alarmları temizle
        sharedPreferences.edit().remove("alarms").apply()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            this,
            { _, hourOfDay, minute ->
                selectedHour = hourOfDay
                selectedMinute = minute
                Toast.makeText(this, "Saat seçildi: $hourOfDay:$minute", Toast.LENGTH_SHORT).show()
                // Saat seçimi yapıldıktan sonra ikonun rengini değiştir
                changeButtonIconColor(timeButton, Color.RED)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }
    private fun changeButtonIconColor(button: Button, color: Int) {
        // Butonun solundaki drawable'ı al
        val drawables = button.compoundDrawablesRelative
        val leftDrawable = drawables[0] // Sol ikon

        if (leftDrawable != null) {
            // Drawable'ı sararak (wrap) renk filtresi uygula
            val wrappedDrawable: Drawable = DrawableCompat.wrap(leftDrawable)
            DrawableCompat.setTint(wrappedDrawable, color)

            // Güncellenmiş drawable'ı butona tekrar set et
            button.setCompoundDrawablesRelativeWithIntrinsicBounds(wrappedDrawable, null, null, null)
        }
    }
    private fun addTime() {
        if (alarmCount >= 3) {
            Toast.makeText(this, "En fazla 3 seçim yapabilirsiniz!", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedHour == -1 || selectedMinute == -1) {
            Toast.makeText(this, "Lütfen bir saat seçin!", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedMessage = spinnerCategories.selectedItem.toString()
        if (selectedMessage.isNotEmpty()) {
            // Alarmı ekleyin ve listeyi güncelleyin
            addAlarmView(selectedMessage, selectedHour, selectedMinute)

            // Kategori listesinden seçilen kategoriyi kaldır
            categoriesList.remove(selectedMessage)
            categoryAdapter.notifyDataSetChanged()  // Adapter'i güncelle

            saveAlarms()
            alarmCount++
        }
    }

    private fun saveSettings() {
        sharedPreferences.edit().putBoolean("notification_setup_done", true).apply()
        Toast.makeText(this, "Ayarlar kaydedildi.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun addAlarmView(message: String, hour: Int, minute: Int) {
        val alarmView = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val alarmText = TextView(this).apply {
            text = "$message - $hour:$minute"
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        val cancelButton = Button(this).apply {
            text = "İptal Et"
            setOnClickListener {
                alarmsContainer.removeView(alarmView)
                alarmCount--
                cancelNotification("$message-$hour-$minute")

                if (!categoriesList.contains(message)) {
                    categoriesList.add(message)
                    categoriesList.sort()
                    categoryAdapter.notifyDataSetChanged()
                }

                saveAlarms()
            }
        }

        alarmView.addView(alarmText)
        alarmView.addView(cancelButton)
        alarmsContainer.addView(alarmView)

        scheduleNotification("$message-$hour-$minute", hour, minute, message)
    }

    private fun scheduleNotification(workName: String, hour: Int, minute: Int, message: String) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val now = Calendar.getInstance()
        var delay = calendar.timeInMillis - now.timeInMillis
        if (delay <= 0) {
            delay += 24 * 60 * 60 * 1000
        }

        val inputData = Data.Builder()
            .putString("category", message)
            .putInt("hour", hour)
            .putInt("minute", minute)
            .build()

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            workName,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    private fun cancelNotification(workName: String) {
        WorkManager.getInstance(this).cancelUniqueWork(workName)
    }

    private fun saveAlarms() {
        val editor = sharedPreferences.edit()
        val alarms = mutableSetOf<String>()
        for (i in 0 until alarmsContainer.childCount) {
            val alarmView = alarmsContainer.getChildAt(i) as LinearLayout
            val alarmText = alarmView.getChildAt(0) as TextView
            alarms.add(alarmText.text.toString())
        }
        editor.putStringSet("alarms", alarms)
        editor.apply()
    }

    private fun loadAlarms() {
        val savedAlarms = sharedPreferences.getStringSet("alarms", emptySet()) ?: return
        val usedCategories = mutableSetOf<String>()

        for (alarm in savedAlarms) {
            val parts = alarm.split(" - ")
            if (parts.size == 2) {
                val message = parts[0]
                val timeParts = parts[1].split(":")
                if (timeParts.size == 2) {
                    val hour = timeParts[0].toIntOrNull() ?: continue
                    val minute = timeParts[1].toIntOrNull() ?: continue

                    addAlarmView(message, hour, minute)
                    usedCategories.add(message)  // Kullanılan kategoriyi kaydet
                    alarmCount++
                }
            }
        }

        // Kullanılmayan kategorileri bul ve spinner'ı güncelle
        categoriesList.clear()
        categoriesList.addAll(resources.getStringArray(R.array.categoriesNotification).toList().filter { !usedCategories.contains(it) })
        categoryAdapter.notifyDataSetChanged()

        hideProgressBar()
    }

    override fun onBackPressed() {
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

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        return true
    }
}


/*import android.Manifest
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
        }

        binding.btnSave.setOnClickListener {
            changeButtonBackgroundColor(it)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    showPermissionRationale()
                } else {
                    sharedPreferences.edit().putBoolean("notification_seen", true).apply()
                    navigateToMainActivity()
                    finish()
                }
            } else {
                sharedPreferences.edit().putBoolean("notification_seen", true).apply()
                navigateToMainActivity()
                finish()
            }
        }

        createNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun changeButtonBackgroundColor(button: View) {
        val randomColor = getRandomSoftColor()

        val background = button.background

        if (background is LayerDrawable) {
            for (i in 0 until background.numberOfLayers) {
                val layer = background.getDrawable(i)
                if (layer is GradientDrawable) {
                    if (!originalButtonColors.containsKey(button)) {
                        originalButtonColors[button] = (layer.color?.defaultColor ?: Color.TRANSPARENT)
                    }
                    layer.setColor(randomColor)

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
        val intent = Intent(this, SettingsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
        return true
    }

    private fun navigateToMainActivity() {
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
*/