package com.example.finalproject_waterlog

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalproject_waterlog.converters.Converters
import com.example.finalproject_waterlog.daos.DrinkLogsDao
import com.example.finalproject_waterlog.daos.FlowersDao
import com.example.finalproject_waterlog.daos.UserInfoDao
import com.example.finalproject_waterlog.models.DrinkLog
import com.example.finalproject_waterlog.models.Flower
import com.example.finalproject_waterlog.models.UserInfo

@Database(entities = [UserInfo::class, DrinkLog::class, Flower::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract val userInfoDao: UserInfoDao
    abstract val drinkLogDao: DrinkLogsDao
    abstract val flowerDao: FlowersDao
}