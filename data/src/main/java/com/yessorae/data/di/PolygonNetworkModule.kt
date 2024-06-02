package com.yessorae.data.di

import com.yessorae.data.source.network.polygon.api.PolygonChartApi
import com.yessorae.data.source.network.polygon.common.PolygonConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object PolygonNetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okhttpCallFactory: Call.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(PolygonConstant.BASE_URL)
            .callFactory(okhttpCallFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideChartApi(retrofit: Retrofit): PolygonChartApi =
        retrofit.create(PolygonChartApi::class.java)
}
