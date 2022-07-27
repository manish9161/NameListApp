package com.demo.namelistapp.db.table

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nameitem")
data class NameItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String
)