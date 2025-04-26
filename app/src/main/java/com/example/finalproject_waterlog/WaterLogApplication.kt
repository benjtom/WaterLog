package com.example.finalproject_waterlog

import android.app.Application
import androidx.room.Room
import com.example.finalproject_waterlog.repositories.DrinkLogRepository
import com.example.finalproject_waterlog.repositories.FlowerRepository
import com.example.finalproject_waterlog.repositories.UserInfoRepository

class WaterLogApplication: Application() {
    private val db by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "waterlog-db"
        ).build()
    }
    val userInfoRepository by lazy { UserInfoRepository(db.userInfoDao) }
    val drinkLogRepository by lazy { DrinkLogRepository(db.drinkLogDao) }
    val flowerRepository by lazy { FlowerRepository(db.flowerDao) }
}