package com.example.finalproject_waterlog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class DrinkLog (
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo val date: Date,
    @ColumnInfo val ounces: Int,
)