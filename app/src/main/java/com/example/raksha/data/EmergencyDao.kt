package com.example.raksha.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EmergencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: EmergencyContact)

    @Query("SELECT * FROM emergency_contacts")
    fun getAllContacts(): Flow<List<EmergencyContact>>

    @Query("SELECT * FROM emergency_contacts")
    suspend fun getAllContactsList(): List<EmergencyContact>

    @Delete
    suspend fun delete(contact: EmergencyContact)
}