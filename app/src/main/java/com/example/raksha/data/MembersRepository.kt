package com.example.raksha.data

import android.content.Context
import com.example.raksha.HelperClass

class MembersRepository(private val context: Context) {
    fun getAllMembers(): List<membersData> {
        val db = HelperClass(context)
        return db.getAllMember()
    }
}