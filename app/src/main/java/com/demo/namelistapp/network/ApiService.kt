package com.demo.namelistapp.network

import com.demo.namelistapp.feature_name.data.NameDto
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {

    @GET("names")
    suspend fun getNames(): Response<List<NameDto>>

}