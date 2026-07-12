package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.WaterRecord
import com.example.data.WaterRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class WaterViewModel(private val repository: WaterRepository) : ViewModel() {

    // Objectif quotidien (2 L = 2000 ml)
    val dailyGoalMl: Int = 2000

    // Début de la journée
    private fun getStartOfToday(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    // Liste des consommations d'aujourd'hui
    val todayRecords: StateFlow<List<WaterRecord>> = repository.allRecords
        .map { records ->
            val startOfToday = getStartOfToday()
            records.filter { it.timestamp >= startOfToday }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // Quantité totale bue aujourd'hui
    val totalIntakeMl: StateFlow<Int> = todayRecords
        .map { records -> records.sumOf { it.amountMl } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    // Progression vers l'objectif
    val progress: StateFlow<Float> = totalIntakeMl
        .map { intake ->
            if (intake >= dailyGoalMl) 1f else intake.toFloat() / dailyGoalMl
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0f
        )

    // Ajouter de l'eau
    fun addWater(amountMl: Int = 250) {
        viewModelScope.launch {
            repository.insert(WaterRecord(amountMl = amountMl))
        }
    }

    // Supprimer une consommation
    fun deleteRecord(recordId: Long) {
        viewModelScope.launch {
            repository.deleteById(recordId)
        }
    }

    // Remettre à zéro
    fun resetToday() {
        viewModelScope.launch {
            // Supprimer toutes les données
            repository.clearAll()
        }
    }

    // Créer le ViewModel
    class Factory(private val repository: WaterRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
                return WaterViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}