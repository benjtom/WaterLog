package com.example.finalproject_waterlog.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Flower(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo val type: String
)