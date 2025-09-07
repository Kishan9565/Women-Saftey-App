package com.example.raksha.ui

import android.graphics.Color
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.raksha.R
import com.example.raksha.databinding.ActivityFakeCallBinding

class Fake_Call : AppCompatActivity() {

    private lateinit var binding: ActivityFakeCallBinding
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = ActivityFakeCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get caller name
        val name = intent.getStringExtra("name") ?: "Unknown"
        binding.callerName.text = name

        // Play ringtone
        playRingtone()

        // Button animations
        val pulseAnim = AnimationUtils.loadAnimation(this, R.anim.slide_pulse)
        binding.acceptButton.startAnimation(pulseAnim)
        binding.declineButton.startAnimation(pulseAnim)

        // Accept Button
        binding.acceptButton.setOnClickListener {
            stopRingtone()
            finish()
        }

        // Decline Button
        binding.declineButton.setOnClickListener {
            stopRingtone()
            finish()
        }
    }

    private fun playRingtone() {
        val ringtoneStr = intent.getStringExtra("ringtone")
        val uri: Uri = if (!ringtoneStr.isNullOrEmpty()) {
            Uri.parse(ringtoneStr)
        } else {
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE) // fallback default
        }

        mediaPlayer = MediaPlayer.create(this, uri).apply {
            isLooping = true
            start()
        }
    }

    private fun stopRingtone() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRingtone()
    }

}
