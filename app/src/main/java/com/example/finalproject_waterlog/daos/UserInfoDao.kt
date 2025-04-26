package com.example.finalproject_waterlog.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.finalproject_waterlog.models.UserInfo

@Dao
abstract class UserInfoDao {

    @Query("SELECT * FROM UserInfo WHERE id = 1 LIMIT 1")
    abstract suspend fun getUserInfo(): UserInfo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun upsertUserInfo(userInfo: UserInfo)

    @Delete
    abstract suspend fun deleteUserInfo(userInfo: UserInfo)
}