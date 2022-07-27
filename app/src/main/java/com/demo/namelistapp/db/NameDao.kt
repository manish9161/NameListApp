package com.demo.namelistapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.namelistapp.db.table.NameItem
import kotlinx.coroutines.flow.Flow

@Dao
interface NameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNameItem(nameTable: NameItem)

    @Query("select * from nameitem")
    fun getNameItems(): Flow<List<NameItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNameItems(nameItems: List<NameItem>)

    @Query("delete from nameitem")
    fun deleteAllNameItems()

}