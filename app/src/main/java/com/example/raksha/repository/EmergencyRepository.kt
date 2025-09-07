package com.example.raksha.repository


import com.example.raksha.data.EmergencyContact
import com.example.raksha.data.EmergencyDao
import kotlinx.coroutines.flow.Flow

class EmergencyRepository(private val dao: EmergencyDao) {

    val allContacts: Flow<List<EmergencyContact>> = dao.getAllContacts()

    suspend fun insert(contact: EmergencyContact) {
        dao.insert(contact)
    }

    suspend fun delete(contact: EmergencyContact) {
        dao.delete(contact)
    }
}
