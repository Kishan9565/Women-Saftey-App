package com.example.raksha.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RuralHelpResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resources: List<RuralHelpResource>)

    @Query("SELECT * FROM rural_help_resources WHERE issueType = :category")
    fun getResourcesByCategory(category: String): Flow<List<RuralHelpResource>>

    @Query("SELECT * FROM rural_help_resources")
    fun getAllResources(): Flow<List<RuralHelpResource>>

    @Query("SELECT * FROM rural_help_resources")
    suspend fun getAllResourcesOnce(): List<RuralHelpResource>
}

