package com.yessorae.chartrainer.fake

import com.yessorae.domain.common.ChartTrainerLogger

class FakeChartTrainerLogger : ChartTrainerLogger {
    val throwableList = mutableListOf<Throwable>()
    override fun cehLog(throwable: Throwable) {
        throwableList.add(throwable)
        // 기획자가 요청한 데이터가 있다면 그것을 mocking 하고 FakeChartTrainerLogger 을 삭제하고 실제 ChartTrainerLogger 사용
    }
}
