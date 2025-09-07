package com.example.raksha.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import android.content.pm.ServiceInfo
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.raksha.R
import com.example.raksha.utils.Constants
import com.example.raksha.utils.PendingState
import com.example.raksha.worker.SosAutoSendWorker
import kotlin.math.sqrt

class ShakeService : Service(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lastAccelMag = 0f
    private var lastUpdate = 0L
    private val shakeThreshold = 15f
    private val sampleMinInterval = 120L
    private val cooldown = 60_000L
    private var lastAlertAt = 0L

    override fun onCreate() {
        super.onCreate()
        createServiceChannel()
        startForegroundServiceCompat()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.registerListener(
            this,
            sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    private fun createServiceChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(
                NotificationChannel(
                    Constants.CHANNEL_SERVICE_ID,
                    Constants.CHANNEL_SERVICE_NAME,
                    NotificationManager.IMPORTANCE_LOW
                )
            )
            nm.createNotificationChannel(
                NotificationChannel(
                    Constants.CHANNEL_SOS_ID,
                    Constants.CHANNEL_SOS_NAME,
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
        }
    }

    private fun buildServiceNotification(): Notification {
        return NotificationCompat.Builder(this, Constants.CHANNEL_SERVICE_ID)
            .setSmallIcon(R.drawable.newlogo)
            .setContentTitle("Raksha active")
            .setContentText("Shake detection running")
            .setOngoing(true)
            .build()
    }

    private fun startForegroundServiceCompat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ requires explicit foregroundServiceType for location
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                startForeground(
                    Constants.ID_SERVICE_NOTIFICATION,
                    buildServiceNotification(),
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
                )
            } else {
                startForeground(Constants.ID_SERVICE_NOTIFICATION, buildServiceNotification())
            }
        } else {
            startForeground(Constants.ID_SERVICE_NOTIFICATION, buildServiceNotification())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type != Sensor.TYPE_ACCELEROMETER) return

        val now = System.currentTimeMillis()
        if (now - lastUpdate < sampleMinInterval) return
        lastUpdate = now

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val mag = sqrt(x * x + y * y + z * z)
        val delta = kotlin.math.abs(mag - lastAccelMag)
        lastAccelMag = mag

        if (delta > shakeThreshold && now - lastAlertAt > cooldown) {
            lastAlertAt = now

            if (!PendingState.isPending(this)) {
                PendingState.setPending(this, true)
                PendingState.markTriggeredAt(this, now)
                SosNotification.showConfirmation(this)

                val req = OneTimeWorkRequestBuilder<SosAutoSendWorker>()
                    .setInitialDelay(java.time.Duration.ofMinutes(1))
                    .build()

                WorkManager.getInstance(this).enqueueUniqueWork(
                    Constants.WORK_SOS_AUTO_SEND,
                    ExistingWorkPolicy.REPLACE,
                    req
                )
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
}
