package com.example.applicationhydratation.data

import com.example.applicationhydratation.ui.WaterViewModel
import kotlinx.coroutines.flow.Flow

class WaterRepository(private val waterDao: WaterViewModel) {
    val allRecords: Flow<List<WaterRecord>> = waterDao.getAllRecords()

    suspend fun insert(record: WaterRecord) {
        waterDao.insertRecord(record)
    }

    suspend fun deleteById(id: Long) {
        waterDao.deleteRecordById(id)
    }

    suspend fun clearAll() {
        waterDao.deleteAllRecords()
    }
}