package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {
    @Query("SELECT * FROM water_records ORDER BY timestamp DESC")
    fun getAllRecords(): Flow<List<WaterRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: WaterRecord)

    @Query("DELETE FROM water_records WHERE id = :id")
    suspend fun deleteRecordById(id: Long)

    @Query("DELETE FROM water_records")
    suspend fun deleteAllRecords()
}