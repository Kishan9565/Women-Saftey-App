package com.example.raksha.adapters

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.Manifest
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.raksha.data.EmergencyContact
import com.example.raksha.databinding.ItemEmergencyBinding

class EmergencyAdapter(
    private var contacts: List<EmergencyContact>,
    private val onDeleteClick: (EmergencyContact) -> Unit
) : RecyclerView.Adapter<EmergencyAdapter.EmergencyViewHolder>() {

    inner class EmergencyViewHolder(val binding: ItemEmergencyBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmergencyViewHolder {
        val binding = ItemEmergencyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmergencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmergencyViewHolder, position: Int) {
        val contact = contacts[position]
        holder.binding.tvName.text = contact.name
        holder.binding.tvNumber.text = contact.number
        holder.binding.btnDelete.setOnClickListener { onDeleteClick(contact) }

        holder.binding.btnCall.setOnClickListener {
            val context = holder.binding.root.context
            val intent = Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:${contact.number}")
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    (context as Activity),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    101
                )
            } else {
                context.startActivity(intent)
            }
        }

    }

    override fun getItemCount(): Int = contacts.size

    fun updateList(newList: List<EmergencyContact>) {
        contacts = newList
        notifyDataSetChanged()
    }
}
