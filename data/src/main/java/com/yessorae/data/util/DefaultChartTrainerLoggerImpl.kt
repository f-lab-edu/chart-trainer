package com.yessorae.data.util

import android.util.Log
import com.yessorae.data.BuildConfig
import com.yessorae.domain.common.ChartTrainerLogger
import javax.inject.Inject

// TODO::LATER 모듈 더 나누는 작업 후, common 모듈로 이동
class DefaultChartTrainerLoggerImpl @Inject constructor() : ChartTrainerLogger {
    override fun cehLog(throwable: Throwable) {
        if (!BuildConfig.DEBUG) return
        Log.d(TAG, "[Throwable]\n$throwable")
        Log.d(TAG, "[StackTrace]")
        Log.d(
            TAG,
            Thread.currentThread().stackTrace
                .take(STACK_TRACE_DEPTH)
                .joinToString("\n") { it.toString() }
        )
        // crashlytics 같은 것을 추가할 수 있음
    }


    companion object {
        const val TAG = "SR-N"
        const val STACK_TRACE_DEPTH = 5
    }
}