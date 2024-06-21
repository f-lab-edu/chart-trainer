package com.yessorae.domain.entity

import com.yessorae.domain.common.DefaultValues
import com.yessorae.domain.entity.value.Money
import kotlinx.serialization.Serializable

@Serializable
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
    val rateOfWinning: Double = if (winCount + loseCount == 0) {
        0.0
    } else {
        winCount / (winCount + loseCount).toDouble()
    }

    val rateOfLosing: Double = if (rateOfWinning == 0.0) {
        0.0
    } else {
        1 - rateOfWinning
    }

    fun quiteGame(): User {
        return this.copy(
            loseCount = loseCount + 1,
        )
    }

    fun finishGame(
        profit: Double,
        rateOfProfit: Double
    ): User {
        val oldTotalGameCount = winCount + loseCount
        return User(
            balance = balance + Money(profit),
            winCount = winCount + (if (profit > 0) 1 else 0),
            loseCount = loseCount + (if (profit < 0) 1 else 0),
            averageRateOfProfit =
            ((averageRateOfProfit * oldTotalGameCount) + rateOfProfit) / (oldTotalGameCount + 1)
        )
    }

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
