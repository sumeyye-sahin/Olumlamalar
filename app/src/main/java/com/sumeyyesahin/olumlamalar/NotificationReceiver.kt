package com.sumeyyesahin.olumlamalar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val category = intent.getStringExtra("category") ?: return
        val dbHelper = DBHelper(context)
        val affirmation = dbHelper.getRandomAffirmationByCategory(category)

        val notificationHelper = NotificationHelper(context)
        notificationHelper.generateNotification("Kendime Not:", affirmation)
    }
}
