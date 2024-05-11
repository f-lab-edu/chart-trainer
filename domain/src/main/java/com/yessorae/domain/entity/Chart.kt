package com.yessorae.domain.entity

import com.yessorae.domain.entity.tick.Tick
import java.time.LocalDateTime

data class Chart(
    val id: Int,
    val tickerSymbol: String, // ex. AAPL
    val startDateTime: LocalDateTime, // 차트 시작 날짜
    val endDateTime: LocalDateTime, // 차트 끝 날짜
    val ticks: List<Tick>, // 차트 데이터
)