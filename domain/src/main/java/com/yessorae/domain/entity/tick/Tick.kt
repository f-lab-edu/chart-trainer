package com.yessorae.domain.entity.tick

import com.yessorae.domain.entity.value.Money
import java.time.LocalDateTime

data class Tick(
    // 시가
    val openPrice: Money,
    // 최고가
    val maxPrice: Money,
    // 최저가
    val minPrice: Money,
    // 종가
    val closePrice: Money,
    // 거래 횟수
    val transactionCount: Int,
    // The Unix Msec timestamp for the start of the aggregate window.
    val startTimestamp: LocalDateTime,
    // 거래량. The trading volume of the symbol in the given time period.
    val tradingVolume: Int,
    // 거래량가중평균가격.
    // Volume Weighted Average Price의 약어로,
    // 조회 대상 종목의 거래일에 발생한 전체 거래대금을 전체 거래량으로 나눈 가격을 의미
    val volumeWeightedAveragePrice: Money
)
