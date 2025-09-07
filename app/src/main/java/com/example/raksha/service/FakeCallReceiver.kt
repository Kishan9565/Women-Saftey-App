package com.example.raksha.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.raksha.ui.Fake_Call

class FakeCallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val name = intent.getStringExtra("name") ?: "Unknown"
        val ringtoneUriStr = intent.getStringExtra("ringtone") ?: ""

        val i = Intent(context, Fake_Call::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("name", name)
            putExtra("ringtone", ringtoneUriStr)
        }

        context.startActivity(i)
    }
}
