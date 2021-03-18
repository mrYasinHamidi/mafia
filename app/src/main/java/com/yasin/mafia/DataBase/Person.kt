
package com.yasin.mafia.DataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Person")
data class Person(
    @PrimaryKey(autoGenerate = true)
    var personId: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean = false

)
