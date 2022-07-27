package com.demo.namelistapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.demo.namelistapp.db.table.NameItem

@Database(entities = [NameItem::class], exportSchema = false, version = 1)
abstract class NameDatabase: RoomDatabase() {
    abstract fun getNameDao(): NameDao
}