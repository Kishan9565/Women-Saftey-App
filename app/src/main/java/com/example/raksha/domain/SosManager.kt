package com.example.raksha.domain

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.telephony.SmsManager
import android.widget.Toast
import com.example.raksha.data.MembersRepository
import com.example.raksha.data.membersData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class SosManager(private val context: Context) {
    private val repo = MembersRepository(context)
    private val fused: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    private suspend fun getLastLocation(): Location? = suspendCancellableCoroutine { cont ->
        fused.lastLocation
            .addOnSuccessListener { cont.resume(it) }
            .addOnFailureListener { cont.resume(null) }
    }
    private fun buildMessage(base: String, loc: Location?): String {
        return if (loc != null) {
            val link = "https://maps.google.com/?q=${loc.latitude},${loc.longitude}"
            "$base\n\nMy location: $link"
        } else {
            "$base\n\n(My location couldn't be fetched)"
        }
    }
    suspend fun sendSosToAllMembers() {
        val members = repo.getAllMembers()
        val loc = try { getLastLocation() } catch (_: Exception) { null }
        if (members.isEmpty()) {
            Toast.makeText(context, "No trusted members saved", Toast.LENGTH_SHORT).show()
            return
        }
        members.forEach { m ->
            val msg = buildMessage(m.msg, loc)
            sendSms(m, msg)
        }
        Toast.makeText(context, "SOS sent to ${members.size} members",
            Toast.LENGTH_SHORT).show()
    }
    private fun sendSms(member: membersData, message: String) {
        try {
            val sms = SmsManager.getDefault()

            val parts = sms.divideMessage(message)
            sms.sendMultipartTextMessage(member.phoneNumber, null, parts, null, null)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Failed to send to ${member.phoneNumber}: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
