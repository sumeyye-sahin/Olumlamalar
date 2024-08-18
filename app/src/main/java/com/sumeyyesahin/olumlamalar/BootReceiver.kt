package com.sumeyyesahin.olumlamalar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val language = sharedPreferences.getString("language", "en") ?: "en"
            updateCategoriesInSharedPreferences(context, language)
            val serializedData = sharedPreferences.getString("categories_and_times", "") ?: return
            val categoriesAndTimes = deserializeCategoriesAndTimes(serializedData)

            categoriesAndTimes.forEach { (category, times) ->
                times.forEach { time ->
                    NotificationReceiver.setDailyNotification(context, time.first, time.second, category)
                }
            }

            Toast.makeText(context, "Notifications rescheduled after reboot", Toast.LENGTH_SHORT).show()
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
                    val updatedCategory = LocaleHelper.getLocalizedCategoryName(context, category, language)
                    "$updatedCategory,$hour,$minute"
                } else {
                    item
                }
            }.joinToString(";")
            editor.putString("categories_and_times", updatedItems)
        }
        editor.apply()
    }

    private fun deserializeCategoriesAndTimes(data: String): Map<String, List<Pair<Int, Int>>> {
        val categoriesAndTimes = mutableMapOf<String, MutableList<Pair<Int, Int>>>()
        val items = data.split(";")
        for (item in items) {
            val parts = item.split(",")
            if (parts.size == 3) {
                val category = parts[0]
                val hour = parts[1].toIntOrNull()
                val minute = parts[2].toIntOrNull()
                if (hour != null && minute != null) {
                    categoriesAndTimes.getOrPut(category) { mutableListOf() }.add(hour to minute)
                }
            }
        }
        return categoriesAndTimes
    }
}
