package com.sumeyyesahin.olumlamalar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val sharedPreferences = applicationContext.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString("language", "en") ?: "en"
        val title = inputData.getString("title")
        val message = inputData.getString("message")

        val localizedTitle = getLocalizedCategoryName(title ?: "", language)
        sendNotification(localizedTitle, message)
        return Result.success()
    }

    private fun sendNotification(title: String, messageBody: String?) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val channelId = "default_channel_id"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.favori_icon)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0, notificationBuilder.build())
    }

    private fun getLocalizedCategoryName(category: String, language: String): String {
        val resources = when (language) {
            "tr" -> LocaleHelper.setLocale(applicationContext, "tr").resources
            "en" -> LocaleHelper.setLocale(applicationContext, "en").resources
            else -> LocaleHelper.setLocale(applicationContext, "en").resources
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
}
