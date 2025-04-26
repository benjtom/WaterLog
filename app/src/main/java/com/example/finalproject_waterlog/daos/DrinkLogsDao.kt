package com.example.finalproject_waterlog.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject_waterlog.models.DrinkLog

@Dao
abstract class DrinkLogsDao {

    @Query("SELECT * FROM DrinkLog")
    abstract suspend fun getAllDrinkLogs(): List<DrinkLog>

    @Insert
    abstract suspend fun insertDrinkLog(drinkLog: DrinkLog): Long

    @Query("SELECT COUNT(*) FROM DrinkLog")
    abstract suspend fun getRowCount(): Int

    @Delete
    abstract suspend fun deleteDrinkLog(drinkLog: DrinkLog)

    @Query("DELETE FROM DrinkLog")
    abstract suspend fun deleteAllDrinkLogs()
}