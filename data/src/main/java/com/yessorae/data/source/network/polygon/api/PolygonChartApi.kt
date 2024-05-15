package com.yessorae.data.source.network.polygon.api

import com.yessorae.data.BuildConfig
import com.yessorae.data.source.network.polygon.common.PolygonConstant
import com.yessorae.data.source.network.polygon.common.PolygonConstant.ADJUSTED_QUERY_KEY
import com.yessorae.data.source.network.polygon.common.PolygonConstant.API_KEY_QUERY_KEY
import com.yessorae.data.source.network.polygon.common.PolygonConstant.DEFAULT_MULTIPLIER_PATH_VALUE
import com.yessorae.data.source.network.polygon.common.PolygonConstant.FROM_PATH_NAME
import com.yessorae.data.source.network.polygon.common.PolygonConstant.GET_CHART_PATH
import com.yessorae.data.source.network.polygon.common.PolygonConstant.MULTIPLIER_PATH_NAME
import com.yessorae.data.source.network.polygon.common.PolygonConstant.SORT_QUERY_KEY
import com.yessorae.data.source.network.polygon.common.PolygonConstant.STOCK_TICKER_PATH_NAME
import com.yessorae.data.source.network.polygon.common.PolygonConstant.TIME_SPAN_PATH_NAME
import com.yessorae.data.source.network.polygon.common.PolygonConstant.TO_PATH_NAME
import com.yessorae.data.source.network.polygon.model.chart.ChartDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PolygonChartApi {
    @GET(GET_CHART_PATH)
    suspend fun getChartData(
        @Path(STOCK_TICKER_PATH_NAME) ticker: String,
        @Path(TIME_SPAN_PATH_NAME) timeSpan: String,
        @Path(FROM_PATH_NAME) from: String,
        @Path(TO_PATH_NAME) to: String,
        @Path(MULTIPLIER_PATH_NAME) multiplier: Int = DEFAULT_MULTIPLIER_PATH_VALUE,
        @Query(ADJUSTED_QUERY_KEY) adjusted: Boolean = true,
        @Query(SORT_QUERY_KEY) sort: String = PolygonConstant.DEFAULT_SORT_QUERY_VALUE,
        @Query(API_KEY_QUERY_KEY) apiKey: String = BuildConfig.POLYGON_API_KEY
    ): ChartDto
}