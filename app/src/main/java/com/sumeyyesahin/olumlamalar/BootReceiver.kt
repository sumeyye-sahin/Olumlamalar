package com.sumeyyesahin.olumlamalar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.Calendar

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val language = sharedPreferences.getString("language", "en") ?: "en"
            updateCategoriesInSharedPreferences(context, language)
            val serializedData = sharedPreferences.getString("categories_and_times", "") ?: return
            val categoriesAndTimes = deserializeCategoriesAndTimes(serializedData)

            for ((category, time) in categoriesAndTimes) {
                setDailyNotification(context, time.first, time.second, category)
            }
        }
    }

    private fun setDailyNotification(context: Context, hour: Int, minute: Int, category: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as android.app.AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("category", category)
        }
        val pendingIntent = android.app.PendingIntent.getBroadcast(
            context, category.hashCode(), intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DATE, 1)
            }
        }

        alarmManager.setRepeating(
            android.app.AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            android.app.AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
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

    private fun updateCategoriesInSharedPreferences(context: Context, language: String) {
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val serializedData = sharedPreferences.getString("categories_and_times", "")
        if (!serializedData.isNullOrEmpty()) {
            val updatedItems = serializedData.split(";").map { item ->
                val parts = item.split(",")
                if (parts.size == 3) {
                    val category = parts[0]
                    val hour = parts[1].toInt()
                    val minute = parts[2].toInt()
                    val updatedCategory = getLocalizedCategoryName(context, category, language)
                    "$updatedCategory,$hour,$minute"
                } else {
                    item
                }
            }.joinToString(";")
            editor.putString("categories_and_times", updatedItems)
        }
        editor.apply()
    }

    private fun deserializeCategoriesAndTimes(data: String): List<Pair<String, Pair<Int, Int>>> {
        return data.split(";").mapNotNull {
            val parts = it.split(",")
            if (parts.size == 3) {
                val category = parts[0]
                val hour = parts[1].toIntOrNull()
                val minute = parts[2].toIntOrNull()
                if (hour != null && minute != null) {
                    category to (hour to minute)
                } else {
                    null
                }
            } else {
                null
            }
        }
    }
}
