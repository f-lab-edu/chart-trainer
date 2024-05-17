package com.yessorae.domain.entity

import com.yessorae.domain.entity.tick.Tick
import com.yessorae.domain.entity.tick.TickUnit
import java.time.LocalDateTime

data class Chart(
    val id: Long = 0,
    // ex. AAPL
    val tickerSymbol: String,
    // 차트 시작 날짜
    val startDateTime: LocalDateTime,
    // 차트 끝 날짜
    val endDateTime: LocalDateTime,
    // 차트 데이터
    val ticks: List<Tick>,
    val tickUnit: TickUnit
)
