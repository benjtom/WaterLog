package com.example.finalproject_waterlog.repositories

import com.example.finalproject_waterlog.daos.DrinkLogsDao
import com.example.finalproject_waterlog.models.DrinkLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

class DrinkLogRepository(
    private val drinkLogsDao: DrinkLogsDao
) {
    private val _drinkLogs = MutableStateFlow(emptyList<DrinkLog>())
    val drinkLogs: StateFlow<List<DrinkLog>> = _drinkLogs

    suspend fun loadDrinkLogs() {
        _drinkLogs.value = drinkLogsDao.getAllDrinkLogs()
    }

    suspend fun addDrinkLog(
        date: Date,
        ounces: Int
    ) {
        val newDrinkLog = DrinkLog(
            date = date,
            ounces = ounces
        )
        newDrinkLog.id = drinkLogsDao.insertDrinkLog(newDrinkLog)
        _drinkLogs.value += newDrinkLog
    }

    suspend fun deleteDrinkLogs() {
        drinkLogsDao.deleteAllDrinkLogs()
        _drinkLogs.value = emptyList()
    }

}