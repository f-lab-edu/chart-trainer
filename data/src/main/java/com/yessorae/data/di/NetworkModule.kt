package com.yessorae.data.di

import com.yessorae.data.BuildConfig
import com.yessorae.data.source.network.polygon.api.PolygonChartApi
import com.yessorae.data.source.network.polygon.common.PolygonConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpCallFactory(): Call.Factory =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        if (BuildConfig.DEBUG) {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                        }
                    }
            )
            .build()

    @Provides
    @Singleton
    fun providePolygonRetrofit(okhttpCallFactory: Call.Factory): Retrofit =
        Retrofit.Builder()
            .baseUrl(PolygonConstant.BASE_URL)
            .callFactory(okhttpCallFactory)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePolygonChartApi(retrofit: Retrofit): PolygonChartApi =
        retrofit.create(PolygonChartApi::class.java)
}
