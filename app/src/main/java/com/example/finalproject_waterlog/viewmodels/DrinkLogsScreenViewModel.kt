package com.example.finalproject_waterlog.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject_waterlog.WaterLogApplication
import com.example.finalproject_waterlog.repositories.DrinkLogRepository
import com.example.finalproject_waterlog.models.DrinkLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Calendar

data class DrinkLogGroup(
    val date: Date,
    val logs: List<DrinkLog>
)

class DrinkLogsScreenViewModel(
    private val drinkLogRepository: DrinkLogRepository
): ViewModel() {
    private val _drinkLogGroups = MutableStateFlow<List<DrinkLogGroup>>(emptyList())
    val drinkLogGroups: StateFlow<List<DrinkLogGroup>> = _drinkLogGroups

    private val dateFormat = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())

    fun loadDrinkLogs() {
        viewModelScope.launch {
            drinkLogRepository.loadDrinkLogs()
            drinkLogRepository.drinkLogs.collect { logs ->
                // Group logs by date
                val groupedLogs = logs.groupBy { log ->
                    // Reset time part to group by day only
                    val calendar = Calendar.getInstance()
                    calendar.time = log.date
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    calendar.time
                }.map { (date, logs) ->
                    DrinkLogGroup(date, logs.sortedByDescending { it.date })
                }.sortedByDescending { it.date }

                _drinkLogGroups.value = groupedLogs
            }
        }
    }

    fun formatDate(date: Date): String {
        return dateFormat.format(date)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as WaterLogApplication
                DrinkLogsScreenViewModel(
                    application.drinkLogRepository
                )
            }
        }
    }
} 