package com.demo.namelistapp.feature_name.domain

import com.demo.namelistapp.db.table.NameItem
import com.demo.namelistapp.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NameRepository {

    fun getNames(): Flow<NetworkResult<List<NameItem>>>
}