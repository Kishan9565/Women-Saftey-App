package com.example.raksha.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.raksha.utils.PendingState
import com.example.raksha.domain.SosManager
import com.example.raksha.service.SosNotification

class SosAutoSendWorker(
    private val appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {

        return try {
            if (PendingState.isPending(appContext)) {
                SosNotification.clear(appContext)
                SosManager(appContext).sendSosToAllMembers()
                PendingState.clear(appContext)
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
