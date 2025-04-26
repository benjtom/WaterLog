package com.example.finalproject_waterlog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserInfo (
    @PrimaryKey val id: Int = 1,
    @ColumnInfo val name: String,
    @ColumnInfo val weight: Int,
    @ColumnInfo val ouncesDrunk: Int,
)