package com.sumeyyesahin.olumlamalar

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroUserNotificationSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Seçilen dili uygulayın
        val selectedLanguage = sharedPreferences.getString("language", "en")
        setLocale(selectedLanguage ?: "en")

        binding.recyclerViewCategoryTimes.layoutManager = LinearLayoutManager(this)
        adapter = CategoryTimeAdapter(categoriesAndTimes)
        binding.recyclerViewCategoryTimes.adapter = adapter

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

    private fun setDailyNotification(hour: Int, minute: Int, category: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("category", category)
        }
        val pendingIntent = PendingIntent.getBroadcast(this, category.hashCode() + hour * 60 + minute, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        // Set the alarm to start at the selected time
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
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
        val intent = Intent(this, MainActivity::class.java)
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
}
