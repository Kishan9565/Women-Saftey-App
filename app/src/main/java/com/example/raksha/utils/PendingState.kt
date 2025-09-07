package com.example.raksha.utils

import android.content.Context
import com.example.raksha.utils.Constants.KEY_SOS_PENDING
import com.example.raksha.utils.Constants.KEY_SOS_TRIGGER_AT
import com.example.raksha.utils.Constants.PREFS

object PendingState {
    fun setPending(context: Context, pending: Boolean) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_SOS_PENDING, pending)
            .apply()
    }
    fun isPending(context: Context): Boolean {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_SOS_PENDING, false)
    }
    fun markTriggeredAt(context: Context, timeMillis: Long) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putLong(KEY_SOS_TRIGGER_AT, timeMillis)
            .apply()
    }
    fun clear(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_SOS_PENDING)
            .remove(KEY_SOS_TRIGGER_AT)
            .apply()
    }
}