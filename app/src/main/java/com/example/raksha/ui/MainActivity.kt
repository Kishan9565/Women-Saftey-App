package com.example.raksha.ui

import android.Manifest
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.raksha.R
import com.example.raksha.databinding.ActivityMainBinding
import com.example.raksha.databinding.DialogConfirmBinding
import com.example.raksha.service.FakeCallReceiver
import com.example.raksha.service.ShakeService
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var selectedTimeInMillis: Long = 0L
    private var selectedRingtone: Uri? = null
// 921457
    private val ringtonePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            selectedRingtone = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI, Uri::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            }
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { _ ->
            startShakeService()
        }

    @RequiresApi(Build.VERSION_CODES.M)
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

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_emergency -> {
                    startActivity(Intent(this, EmergencyActivity::class.java))
                    true
                }
                R.id.nav_rights -> {
                    startActivity(Intent(this, RuralHelpActivity::class.java))
                    true
                }
                else -> false
            }
        }

        val perms = mutableListOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            perms += Manifest.permission.POST_NOTIFICATIONS
        }
        permissionLauncher.launch(perms.toTypedArray())


        binding.addMember.setOnClickListener {
            startActivity(Intent(this, AddMembers::class.java))
        }

        binding.fakeCall.setOnClickListener {
            showFakeCallDialog()
        }

        binding.registerComplaint.setOnClickListener {
            showCustomDialog()
        }

        binding.safeZone.setOnClickListener {
            startActivity(Intent(this, SafetyTipsActivity::class.java))
        }
    }

    private fun startShakeService() {
        val intent = Intent(this, ShakeService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showFakeCallDialog() {
        val dialogView = layoutInflater.inflate(R.layout.fake_call_dialog, null)
        val etName = dialogView.findViewById<EditText>(R.id.etName)
        val btnSetTime = dialogView.findViewById<Button>(R.id.btnSetTime)
        val btnSetRingtone = dialogView.findViewById<Button>(R.id.btnSetRingtone)
        val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        btnSetTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val timePickerDialog = android.app.TimePickerDialog(
                this,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    calendar.set(Calendar.SECOND, 0)
                    if (calendar.timeInMillis <= System.currentTimeMillis()) {
                        calendar.add(Calendar.DAY_OF_MONTH, 1)
                    }
                    selectedTimeInMillis = calendar.timeInMillis
                    Toast.makeText(this, "Time Set!", Toast.LENGTH_SHORT).show()
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }

        btnSetRingtone.setOnClickListener {
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone")
            ringtonePickerLauncher.launch(intent)
        }

        btnConfirm.setOnClickListener {
            val name = etName.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter Name!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedTimeInMillis == 0L) {
                Toast.makeText(this, "Set Time!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            scheduleFakeCall(name, selectedRingtone, selectedTimeInMillis)
            Toast.makeText(this, "Fake Call Scheduled!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(true)
        dialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun scheduleFakeCall(name: String, ringtone: Uri?, timeInMillis: Long) {
        val intent = Intent(this, FakeCallReceiver::class.java)
        intent.putExtra("name", name)
        intent.putExtra("ringtone", ringtone.toString())
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
    }

    private fun showCustomDialog() {
        val dialog = Dialog(this)
        val binding = DialogConfirmBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.tvTitle.text = "Confirm Complaint Registration"
        binding.tvMessage.text = "क्या आप सच में शिकायत दर्ज़ करना चाहते हैं?"

        binding.btnYes.setOnClickListener {
            dialog.dismiss()
            openWebView()
        }

        binding.btnNo.setOnClickListener {
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun openWebView() {
        val intent = Intent(this, RegisterComplaint::class.java)
        intent.putExtra("url", "https://ncwapps.nic.in/onlinecomplaintsv2/frmPubRegistration.aspx")
        startActivity(intent)
    }
}
