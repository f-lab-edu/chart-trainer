package com.yessorae.data.source.network.polygon.common

/**
 * polygon.io 의 API 요청과 응답에 필요한 상수들의 모음
 * [API 문서 참고](https://polygon.io/docs/stocks/getting-started)
 */
object PolygonConstant {
    const val BASE_URL = "https://api.polygon.io/"

    const val GET_CHART_PATH =
        "/v2/aggs/ticker/{stocksTicker}/range/{multiplier}/{timespan}/{from}/{to}"
    const val STOCK_TICKER_PATH_NAME = "stocksTicker"
    const val MULTIPLIER_PATH_NAME = "multiplier"
    const val TIME_SPAN_PATH_NAME = "timespan"
    const val FROM_PATH_NAME = "from"
    const val TO_PATH_NAME = "to"
    const val ADJUSTED_QUERY_KEY = "adjusted"
    const val SORT_QUERY_KEY = "sort"
    const val API_KEY_QUERY_KEY = "apiKey"

    const val DEFAULT_SORT_QUERY_VALUE = "asc"
    const val DEFAULT_MULTIPLIER_PATH_VALUE = 1
    const val TIME_SPAN_DAY_PATH_VALUE = "day"
    const val TIME_SPAN_HOUR_PATH_VALUE = "hour"
}
