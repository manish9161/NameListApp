package com.demo.namelistapp.feature_name.domain

import com.demo.namelistapp.core.mapInPlace
import com.demo.namelistapp.db.table.NameItem
import com.demo.namelistapp.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class GetNamesUseCase @Inject constructor(
    private val repository: NameRepository
) {

    operator fun invoke(): Flow<NetworkResult<List<NameItem>>> {

        val networkResultFlow = repository.getNames()
        return networkResultFlow.map { networkResult ->
            if(networkResult is NetworkResult.Success) {
                if(networkResult.data != null) {
                    val list = networkResult.data!!.toMutableList()
                    list.mapInPlace { element ->
                        NameItem(element.id, element.name.replaceFirstChar { firstChar ->
                            if (firstChar.isLowerCase()) firstChar.titlecase(
                                Locale.getDefault()
                            ) else firstChar.toString()
                        })
                    }
                    networkResult.data = list
                }
            }
            networkResult
        }
    }
}