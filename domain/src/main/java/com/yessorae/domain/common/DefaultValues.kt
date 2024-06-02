package com.yessorae.domain.common

import com.yessorae.domain.entity.tick.TickUnit

object DefaultValues {
    const val DEFAULT_COMMISSION_RATE = 0.1
    const val DEFAULT_TOTAL_TURN = 50
    const val FIRST_CURRENT_BALANCE = 1000.0
    val defaultTickUnit = TickUnit.DAY
}
