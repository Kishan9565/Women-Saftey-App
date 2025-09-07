package com.example.raksha.repository

import androidx.lifecycle.LiveData
import com.example.raksha.data.RuralHelpResource
import com.example.raksha.data.RuralHelpResourceDao
import kotlinx.coroutines.flow.Flow

class RuralHelpRepository(private val dao: RuralHelpResourceDao) {

    fun getAllResources(): Flow<List<RuralHelpResource>> {
        return dao.getAllResources()
    }

    fun getByCategory(category: String): Flow<List<RuralHelpResource>> {
        return dao.getResourcesByCategory(category)
    }

    suspend fun insertAll(resources: List<RuralHelpResource>) {
        dao.insertAll(resources)
    }
}

