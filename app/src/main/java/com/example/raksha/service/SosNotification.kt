package com.example.raksha.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.raksha.R
import com.example.raksha.utils.Constants

object SosNotification {
    fun showConfirmation(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        val confirmIntent = Intent(context, SosActionReceiver::class.java).apply {
            action = Constants.ACTION_SOS_CONFIRM
        }
        val cancelIntent = Intent(context, SosActionReceiver::class.java).apply {
            action = Constants.ACTION_SOS_CANCEL
        }
        val confirmPI = PendingIntent.getBroadcast(
            context, 0, confirmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val cancelPI = PendingIntent.getBroadcast(
            context, 1, cancelIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, Constants.CHANNEL_SOS_ID)
            .setSmallIcon(R.drawable.sos) // apna icon
            .setContentTitle("Shake detected")
            .setContentText("Send SOS to your trusted contacts?")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(R.drawable.send, "Confirm", confirmPI)
            .addAction(R.drawable.canc, "Cancel", cancelPI)
            .setAutoCancel(true)
            .build()
        nm.notify(Constants.ID_SOS_NOTIFICATION, notification)
    }
    fun clear(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as
                NotificationManager
        nm.cancel(Constants.ID_SOS_NOTIFICATION)
    }
}