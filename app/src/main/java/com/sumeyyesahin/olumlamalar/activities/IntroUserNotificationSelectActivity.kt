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

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        progressBar = findViewById(R.id.progressBar)
        spinnerCategories = findViewById(R.id.spinnerCategories)
        addButton = findViewById(R.id.btnAddTime)
        saveButton = findViewById(R.id.btnSave)
        alarmsContainer = findViewById(R.id.alarmsContainer)

        checkLanguageChange()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        categoriesList.addAll(resources.getStringArray(R.array.categoriesNotification))
        categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoriesList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategories.adapter = categoryAdapter

        findViewById<Button>(R.id.timeButton).setOnClickListener {
            showTimePickerDialog()
        }

        addButton.setOnClickListener {
            addTime()
        }

        saveButton.setOnClickListener {
            saveSettings()
        }
        timeButton = findViewById(R.id.timeButton)

        timeButton.setOnClickListener {
            showTimePickerDialog()
        }
        showProgressBar()
        loadAlarms()
    }

    private fun checkLanguageChange() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val lastCheckTimestamp = sharedPreferences.getLong("last_checked_language_timestamp", 0L)
        val lastLanguageChangeTimestamp = sharedPreferences.getLong("language_change_timestamp", 0L)

        if (lastLanguageChangeTimestamp > lastCheckTimestamp) {
            cancelAllAlarms()
            sharedPreferences.edit().putLong("last_checked_language_timestamp", System.currentTimeMillis()).apply()
            Toast.makeText(this, "Dil değişimi sonrası bildirimleri güncellemelisin!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelAllAlarms() {
        WorkManager.getInstance(this).cancelAllWork()

        alarmsContainer.removeAllViews()
        alarmCount = 0

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
                changeButtonIconColor(timeButton, Color.RED)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }
    private fun changeButtonIconColor(button: Button, color: Int) {
        val drawables = button.compoundDrawablesRelative
        val leftDrawable = drawables[0] // Sol ikon

        if (leftDrawable != null) {
            val wrappedDrawable: Drawable = DrawableCompat.wrap(leftDrawable)
            DrawableCompat.setTint(wrappedDrawable, color)

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
            addAlarmView(selectedMessage, selectedHour, selectedMinute)

            categoriesList.remove(selectedMessage)
            categoryAdapter.notifyDataSetChanged()

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
                    usedCategories.add(message)
                    alarmCount++
                }
            }
        }

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