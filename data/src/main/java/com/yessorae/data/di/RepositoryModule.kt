package com.yessorae.data.di

import com.yessorae.data.repository.ChartGameRepositoryImpl
import com.yessorae.data.repository.ChartRepositoryImpl
import com.yessorae.data.repository.UserRepositoryImpl
import com.yessorae.domain.repository.ChartGameRepository
import com.yessorae.domain.repository.ChartRepository
import com.yessorae.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsChartGameRepository(
        chartGameRepository: ChartGameRepositoryImpl
    ): ChartGameRepository

    @Binds
    @Singleton
    abstract fun bindsChartRepository(chartRepository: ChartRepositoryImpl): ChartRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(userRepository: UserRepositoryImpl): UserRepository
}
