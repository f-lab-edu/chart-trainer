package com.yessorae.domain.entity.tick

import java.time.LocalDateTime

data class Tick(
    val openPrice: Double, // 시가
    val maxPrice: Double, // 최고가
    val minPrice: Double, // 최저가
    val closePrice: Double, // 종가
    val transactionCount: Int, // 거래 횟수
    val startTimestamp: LocalDateTime, // The Unix Msec timestamp for the start of the aggregate window.
    val tradingVolume: Int, // 거래량. The trading volume of the symbol in the given time period.
    val volumeWeightedAveragePrice: Double, // 거래량가중평균가격. Volume Weighted Average Price의 약어로 조회 대상 종목의 거래일에 발생한 전체 거래대금을 전체 거래량으로 나눈 가격을 의미
    val isOverTheCounter: Boolean, // 장외거래(場外去來, Over-the-counter)는 주식, 채권, 상품선물, 파생금융상품과 같은 투자자산을 거래소(exchange)를 거치지 않고 양 당사자가 직접 거래하는 것을 의미
    val unit: TickUnit
)

