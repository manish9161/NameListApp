package com.demo.namelistapp.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.demo.namelistapp.db.table.NameItem
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class NameDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var nameDatabase: NameDatabase
    private lateinit var nameDao: NameDao


    @Before
    fun setup() {
        nameDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NameDatabase::class.java
        ).allowMainThreadQueries().build()

        nameDao = nameDatabase.getNameDao()
    }

    @After
    fun tearDown() {
        nameDatabase.close()
    }

    @Test
    fun insertNameItem() = runBlockingTest {

        val nameItem1 = NameItem(name = "Test1")
        val nameItem2 = NameItem(name = "Test2")
        val list = mutableListOf<NameItem>()
        list.add(nameItem1)
        list.add(nameItem2)
        nameDao.insertNameItems(list)

        assertThat(nameDao.getNameItems()).isNotNull()

    }

}