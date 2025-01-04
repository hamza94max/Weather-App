package com.hamza.cityinput.di

import com.hamza.cityinput.usecases.SaveInputCityUseCase
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
    fun provideSaveLastSearchedCityUseCase(repository: WeatherRepository): SaveInputCityUseCase {
        return SaveInputCityUseCase(repository)
    }
}