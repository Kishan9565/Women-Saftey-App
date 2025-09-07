package com.example.raksha.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.WorkManager
import com.example.raksha.utils.Constants
import com.example.raksha.utils.PendingState
import com.example.raksha.domain.SosManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SosActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Constants.ACTION_SOS_CONFIRM -> {

                WorkManager.getInstance(context).cancelUniqueWork(Constants.WORK_SOS_AUTO_SEND)
                SosNotification.clear(context)
                if (PendingState.isPending(context)) {
                    CoroutineScope(Dispatchers.Main).launch {
                        SosManager(context).sendSosToAllMembers()
                        PendingState.clear(context)
                    }
                }
            }
            Constants.ACTION_SOS_CANCEL -> {

                WorkManager.getInstance(context).cancelUniqueWork(Constants.WORK_SOS_AUTO_SEND)
                SosNotification.clear(context)
                PendingState.clear(context)
            }
        }
    }
}