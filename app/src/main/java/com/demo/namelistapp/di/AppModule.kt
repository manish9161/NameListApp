package com.demo.namelistapp.di

import android.content.Context
import androidx.room.Room
import com.demo.namelistapp.db.NameDatabase
import com.demo.namelistapp.db.NameDao
import com.demo.namelistapp.feature_name.data.FakeNameRepositoryImpl
import com.demo.namelistapp.feature_name.data.NameRepositoryImpl
import com.demo.namelistapp.feature_name.domain.NameRepository
import com.demo.namelistapp.network.ApiService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://62e0183d98dd9c9df60d9b8e.mockapi.io/api/")
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(OkHttpProfilerInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideNameDatabase(@ApplicationContext app: Context): NameDatabase {
        return Room.databaseBuilder(
            app,
            NameDatabase::class.java,
            "name_app").build()
    }

    @Provides
    @Singleton
    fun provideCartItemDao(db: NameDatabase): NameDao {
        return db.getNameDao()
    }

    @Provides
    @Singleton
    fun provideNameRepository(apiService: ApiService, db: NameDatabase): NameRepository {
        return FakeNameRepositoryImpl(apiService, db)
    }

}