package com.demo.namelistapp.feature_name.data

import androidx.room.withTransaction
import com.demo.namelistapp.core.LogUtils
import com.demo.namelistapp.core.networkBoundResource
import com.demo.namelistapp.db.NameDatabase
import com.demo.namelistapp.feature_name.domain.NameRepository
import com.demo.namelistapp.network.ApiService
import com.demo.namelistapp.network.BaseApiResponse
import com.demo.namelistapp.network.NetworkResult
import javax.inject.Inject

class NameRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val nameDatabase: NameDatabase
): BaseApiResponse(), NameRepository {

    private val nameDao = nameDatabase.getNameDao()

    private suspend fun nameApi(): NetworkResult<List<NameDto>> {
        return safeApiCall { apiService.getNames() }
    }

    override fun getNames() = networkBoundResource(
        query = {
            nameDao.getNameItems()
        },
        fetch = {
            nameApi()
        },
        saveFetchResult = { networkResult ->
            when(networkResult) {
                is NetworkResult.Error -> {
                    LogUtils.printError(networkResult.message.toString())
                }
                is NetworkResult.Success -> {
                    networkResult.data?.let { nameList ->
                        nameDatabase.withTransaction {
                            nameDao.deleteAllNameItems()
                            val nameItemList = nameList.map { it.toNameItem() }
                            nameDao.insertNameItems(nameItemList)
                        }
                    }

                }
                is NetworkResult.Loading -> {

                }
            }
        },
        shouldFetch = { nameList ->
            nameList.isEmpty()
        }
    )

}