package com.yessorae.chartrainer.fake

import com.yessorae.domain.common.ChartRequestArgumentHelper

class FakeChartRequestArgumentHelper : ChartRequestArgumentHelper {
    override fun getRandomTicker(): String = "AAPL"

    override fun getFromDate(): String = "2024-05-20"

    override fun getToDate(): String = "2022-05-20"
}