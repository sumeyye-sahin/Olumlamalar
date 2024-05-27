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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.sumeyyesahin.olumlamalar.databinding.ActivityIntroUserNotificationSelectBinding
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

class IntroUserNotificationSelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroUserNotificationSelectBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val NOTIFICATION_PERMISSION_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroUserNotificationSelectBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

        // Seçilen dili uygulayın
        val selectedLanguage = sharedPreferences.getString("language", "en")
        setLocale(selectedLanguage ?: "en")

        binding.btnSave.setOnClickListener {
            val category = binding.spinnerCategories.selectedItem.toString()
            val hour = binding.timePicker.hour
            val minute = binding.timePicker.minute

            // Save to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("category", category)
            editor.putInt("hour", hour)
            editor.putInt("minute", minute)
            editor.putBoolean("notification_setup_done", true) // Bildirim ayarlarının yapıldığını kaydet
            editor.apply()

            // Set alarm
            setDailyNotification(hour, minute, category)

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
        val currentTime = Calendar.getInstance().timeInMillis
        val targetTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }.timeInMillis

        val delay = targetTime - currentTime

        val inputData = Data.Builder()
            .putString("title", "Olumlama Zamanı")
            .putString("message", "Seçtiğiniz kategori: $category")
            .build()

        val notificationWork = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(notificationWork)
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
}
