package com.example.finalproject_waterlog.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.finalproject_waterlog.models.Flower

@Dao
abstract class FlowersDao {

    @Query("SELECT * FROM Flower")
    abstract suspend fun getAllFlowers(): List<Flower>

    @Insert
    abstract suspend fun insertFlower(flower: Flower): Long

    @Query("SELECT COUNT(*) FROM Flower")
    abstract suspend fun getRowCount(): Int

    @Delete
    abstract suspend fun deleteFlower(flower: Flower)

    @Query("DELETE FROM Flower")
    abstract suspend fun deleteAllFlowers()
}