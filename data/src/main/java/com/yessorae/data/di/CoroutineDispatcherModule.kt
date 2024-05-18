package com.yessorae.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

// TODO::Later common 모듈 만들고 이동

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val niaDispatcher: ChartTrainerDispatcher)

enum class ChartTrainerDispatcher {
    Default,
    IO,
}

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {
    @Provides
    @Dispatcher(ChartTrainerDispatcher.IO)
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(ChartTrainerDispatcher.Default)
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
