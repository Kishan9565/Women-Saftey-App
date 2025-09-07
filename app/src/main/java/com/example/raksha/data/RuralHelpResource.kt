package com.example.raksha.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "rural_help_resources")
data class RuralHelpResource(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val issueType: String,
    val symptoms: List<String>,
    val explanation: String,
    val whereToGo: String,
    val contactNumber: String?,
    val location: String?,
    val language: String,

)
