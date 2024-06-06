package com.yessorae.domain.entity

import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.value.Money

data class User(
    val balance: Money,
    val winCount: Int,
    val loseCount: Int,
    val averageRateOfProfit: Double
//    TODO::LATER 아래 프로퍼티는 로그인 피쳐할 때 추가
//    val isAnonnymous: Boolean,
//    val profileImg: String?,
//    val nickname: String,
) {
    companion object {
        fun createInitialUser() =
            User(
                balance = Money(DefaultValues.FIRST_CURRENT_BALANCE),
                winCount = 0,
                loseCount = 0,
                averageRateOfProfit = 0.0
            )
    }
}
