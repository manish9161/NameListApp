package com.demo.namelistapp.feature_name.data

import androidx.room.withTransaction
import com.demo.namelistapp.core.networkBoundResource
import com.demo.namelistapp.db.NameDatabase
import com.demo.namelistapp.feature_name.domain.NameRepository
import com.demo.namelistapp.network.ApiService
import com.demo.namelistapp.network.BaseApiResponse
import com.demo.namelistapp.network.NetworkResult
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeNameRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val nameDatabase: NameDatabase
): BaseApiResponse(), NameRepository {

    private val nameDao = nameDatabase.getNameDao()

    private fun nameApi(): NetworkResult<List<NameDto>> {
        val gson = Gson()
        val itemType = object : TypeToken<List<NameDto>>() {}.type
        return try {
            val list: List<NameDto> = gson.fromJson(FakeResponses.fakeCVJsonResponse200, itemType)
            NetworkResult.Success(list)
        } catch (e: JsonSyntaxException) {
            NetworkResult.Error(message = "")
        } catch (e: Exception) {
            NetworkResult.Error(message = "")
        }


    }

    override fun getNames() = networkBoundResource(
        query = {
            nameDao.getNameItems()
        },
        fetch = {
            delay(2000)
            nameApi()
        },
        saveFetchResult = { networkResult ->
            when(networkResult) {
                is NetworkResult.Error -> {

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