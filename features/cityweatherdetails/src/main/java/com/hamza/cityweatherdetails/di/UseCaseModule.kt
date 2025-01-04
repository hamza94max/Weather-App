package com.hamza.cityweatherdetails.di

import com.hamza.cityweatherdetails.usecases.GetCurrentWeatherUseCase
import com.hamza.cityweatherdetails.usecases.GetForecastsUseCase
import com.hamza.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCurrentWeatherUseCase(repository: WeatherRepository): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetForecastsUseCase(repository: WeatherRepository): GetForecastsUseCase {
        return GetForecastsUseCase(repository)
    }
}