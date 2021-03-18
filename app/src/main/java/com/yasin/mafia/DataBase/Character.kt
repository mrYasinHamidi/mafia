package com.yasin.mafia.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Character(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val role: String,
    @ColumnInfo
    val image: String,
    @ColumnInfo
    val discription: String,
    @ColumnInfo
    val side: Int,
    @ColumnInfo
    var selected: Boolean = false
)