package com.hamza.data.di

import android.content.Context
import android.content.SharedPreferences
import com.hamza.core.utils.Constants.CITY_SHARED_PREF
import com.hamza.data.local.PreferenceManager
import com.hamza.data.remote.ApiService
import com.hamza.data.remote.WeatherRemoteDataSource
import com.hamza.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(CITY_SHARED_PREF, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferencesManager(sharedPreferences: SharedPreferences): PreferenceManager {
        return PreferenceManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideWeatherRemoteDataSource(apiService: ApiService): WeatherRemoteDataSource {
        return WeatherRemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        remoteDataSource: WeatherRemoteDataSource,
        preferencesManager: PreferenceManager
    ): WeatherRepository {
        return WeatherRepository(remoteDataSource, preferencesManager)
    }
}