package com.example.raksha.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.raksha.adapters.EmergencyAdapter
import com.example.raksha.data.AppDatabase
import com.example.raksha.data.EmergencyContact
import com.example.raksha.databinding.ActivityEmergencyBinding
import com.example.raksha.databinding.DilogSosCallBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EmergencyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmergencyBinding
    private lateinit var adapter: EmergencyAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        binding = ActivityEmergencyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        adapter = EmergencyAdapter(emptyList()) { contact ->
            deleteContact(contact)
        }
        binding.rvEmergency.layoutManager = LinearLayoutManager(this)
        binding.rvEmergency.adapter = adapter

        // Flow ke saath automatically update ho
        lifecycleScope.launch {
            prepopulateDefaultContacts()
            db.emergencyDao().getAllContacts().collect { list ->
                adapter.updateList(list)
            }
        }

        binding.addButton.setOnClickListener {
            showAddDialog()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission Granted. Tap Call again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showAddDialog() {
        val dialogBinding = DilogSosCallBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnSave.setOnClickListener {
            val name = dialogBinding.etName.text.toString().trim()
            val number = dialogBinding.etNumber.text.toString().trim()

            if (name.isNotEmpty() && number.isNotEmpty()) {
                val contact = EmergencyContact(name = name, number = number)
                addContact(contact)
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(true)
        dialog.show()
    }

    private fun addContact(contact: EmergencyContact) {
        lifecycleScope.launch {
            db.emergencyDao().insert(contact)
        }
    }

    private fun deleteContact(contact: EmergencyContact) {
        lifecycleScope.launch {
            db.emergencyDao().delete(contact)
        }
    }

    private suspend fun prepopulateDefaultContacts() {
        val contacts = db.emergencyDao().getAllContactsList() // suspend fun returning List
        if (contacts.isEmpty()) {
            val defaultContacts = listOf(
                EmergencyContact(name = "Police", number = "100"),
                EmergencyContact(name = "Ambulance", number = "102"),
                EmergencyContact(name = "Fire Brigade", number = "101"),
                EmergencyContact(name = "Women Helpline", number = "1091"),
                EmergencyContact(name = "Disaster Management", number = "108")
            )
            defaultContacts.forEach { db.emergencyDao().insert(it) }
        }
    }
}
