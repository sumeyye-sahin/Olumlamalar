package com.sumeyyesahin.olumlamalar.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sumeyyesahin.olumlamalar.services.NotificationWorker
import java.util.concurrent.TimeUnit

class NotificationReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            // Cihaz yeniden başlatıldığında tekrarlayan işi planla
         //  scheduleDailyNotifications(context)
        }
    }

    private fun scheduleDailyNotifications(context: Context) {
        val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.DAYS)
            .addTag("dailyNotification")
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.cancelAllWorkByTag("dailyNotification")
        workManager.enqueue(periodicWorkRequest)
    }
}


/*
class NotificationReceiver : BroadcastReceiver() {
      /* override fun onReceive(context: Context, intent: Intent) {
           val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>().build()
           WorkManager.getInstance(context).enqueue(workRequest)
       }*/
}


   /*
    companion object {
           const val CHANNEL_ID = "affirmation_notification_channel"
           const val CHANNEL_NAME = "Olumlama Bildirim Kanalı"

           fun setDailyNotification(context: Context, hour: Int, minute: Int, category: String) {
               val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
               val intent = Intent(context, NotificationReceiver::class.java).apply {
                   putExtra("category", category)
               }
               val pendingIntent = PendingIntent.getBroadcast(
                   context, getRequestCode(category, hour, minute), intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
               )

               val calendar = Calendar.getInstance().apply {
                   timeInMillis = System.currentTimeMillis()
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

           fun cancelNotification(context: Context, category: String, hour: Int, minute: Int) {
               val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
               val intent = Intent(context, NotificationReceiver::class.java).apply {
                   putExtra("category", category)
               }
               val requestCode = getRequestCode(category, hour, minute)
               val pendingIntent = PendingIntent.getBroadcast(
                   context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
               )

               // AlarmManager ve PendingIntent iptal ediliyor
               alarmManager.cancel(pendingIntent)
               pendingIntent.cancel()

               Log.d("NotificationCancel", "Notification cancelled for $category at $hour:$minute with requestCode $requestCode")
           }

           private fun getRequestCode(category: String, hour: Int, minute: Int): Int {
               return "$category,$hour,$minute".hashCode()
           }
       }

       override fun onReceive(context: Context, intent: Intent) {
           val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
           val language = sharedPreferences.getString("language", "en") ?: "en"

           val localizedContext = LocaleHelper.setLocale(context, language)
           val title = localizedContext.getString(R.string.note_to_myself)
           val originalCategory = intent.getStringExtra("category") ?: return
           Log.d("NotificationReceiver", "Received intent for category: $originalCategory")

           val localizedCategory = LocaleHelper.getLocalizedCategoryName(localizedContext, originalCategory, language)

           val dbHelper = DBHelper(context)
           val affirmation = dbHelper.getRandomAffirmationByCategory(localizedCategory, language)
           Log.d("NotificationReceiver", "Sending notification for category: $localizedCategory")

           sendNotification(localizedContext, title, affirmation)
       }

       private fun sendNotification(context: Context, title: String, message: String) {
           createNotificationChannel(context, CHANNEL_ID, CHANNEL_NAME)

           val intent = Intent(context, ShowAffirmationActivity::class.java).apply {
               addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
               putExtra("affirmation_message", message)
           }
           val pendingIntent = PendingIntent.getActivity(
               context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
           )

           val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
           val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
               .setSmallIcon(R.drawable.favori_icon)
               .setContentTitle(title)
               .setContentText(message)
               .setAutoCancel(true)
               .setSound(defaultSoundUri)
               .setPriority(NotificationCompat.PRIORITY_HIGH)
               .setDefaults(NotificationCompat.DEFAULT_ALL)
               .setContentIntent(pendingIntent)
               .setStyle(NotificationCompat.DecoratedCustomViewStyle())
               .setCustomContentView(getExpandedRemoteView(context, title, message))
               .setCustomBigContentView(getExpandedRemoteView(context, title, message))

           val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

           // Sadece izin verilmişse bildirimi göster
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(context, "android.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
               Toast.makeText(context, "Bildirim izni verilmemiş.", Toast.LENGTH_SHORT).show()
           } else {
               Handler().postDelayed({
                   notificationManager.notify(1, notificationBuilder.build())
               }, 8000)  // 8 saniye gecikmeli gönderim
           }
       }

       private fun createNotificationChannel(context: Context, channelId: String, channelName: String) {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
               val channel = NotificationChannel(
                   channelId,
                   channelName,
                   NotificationManager.IMPORTANCE_HIGH
               ).apply {
                   description = "Olumlama bildirimleri için kullanılır"
               }
               val notificationManager: NotificationManager =
                   context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
               notificationManager.createNotificationChannel(channel)
           }
       }

       private fun getExpandedRemoteView(context: Context, title: String, message: String): RemoteViews {
           val remoteView = RemoteViews(context.packageName, R.layout.custom_notification_layout)
           remoteView.setTextViewText(R.id.title, title)
           remoteView.setTextViewText(R.id.message, message)
           remoteView.setImageViewResource(R.id.image, R.drawable.favori_icon)
           return remoteView
       }*/

*/
