package com.example.raksha.ui

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.raksha.HelperClass
import com.example.raksha.MemebersAdapter
import com.example.raksha.databinding.ActivityAddMembersBinding
import com.example.raksha.databinding.DialogAddMemberBinding
import com.example.raksha.data.membersData

class AddMembers : AppCompatActivity() {

    private lateinit var binding: ActivityAddMembersBinding
    private lateinit var helper: HelperClass
    private lateinit var membersAdapter: MemebersAdapter

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

        binding = ActivityAddMembersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Edge to edge padding
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        helper = HelperClass(this)

        membersAdapter = MemebersAdapter(helper.getAllMember(), this)

        binding.memberRecycleView.layoutManager = LinearLayoutManager(this)
        binding.memberRecycleView.adapter = membersAdapter



        // FloatingActionButton click → show dialog
        binding.addButton.setOnClickListener {
            showAddMemberDialog()
        }
    }

    private fun showAddMemberDialog() {
        val dialogBinding = DialogAddMemberBinding.inflate(LayoutInflater.from(this))
        val dialog = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .create()

        // Dialog Save button click
        dialogBinding.btnSave.setOnClickListener {
            val name = dialogBinding.etName.text.toString()
            val number = dialogBinding.etNumber.text.toString()
            val msg = dialogBinding.etMsg.text.toString()

            if (name.isNotEmpty() && number.isNotEmpty() && msg.isNotEmpty()) {
                // Create member object
                val member = membersData(0, number, name, msg)
                helper.insertMember(member) // Insert into SQL
                Toast.makeText(this, "Member added successfully", Toast.LENGTH_SHORT).show()
                dialog.dismiss() // Close dialog
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(true)
        dialog.show() // Show dialog on screen
    }

    override fun onResume() {
        super.onResume()
        membersAdapter.refreshData(helper.getAllMember())
    }
}
