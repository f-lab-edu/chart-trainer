package com.yessorae.chartrainer.presentation.home

import org.junit.Assert.assertEquals
import org.junit.Test

class UserInfoUiTest {
    @Test
    fun winning_rate_bar_is_shown_when_rate_is_not_zero_and_sum_is_1() {
        var userInfoUi = createUserInfoUi(
            rateOfWinning = 1f,
            rateOfLosing = 0f
        )

        assertEquals(
            true,
            userInfoUi.showWinningRateBar
        )

        userInfoUi = createUserInfoUi(
            rateOfWinning = 0f,
            rateOfLosing = 1f
        )

        assertEquals(
            true,
            userInfoUi.showWinningRateBar
        )

        userInfoUi = createUserInfoUi(
            rateOfWinning = 0.7f,
            rateOfLosing = 0.3f
        )

        assertEquals(
            true,
            userInfoUi.showWinningRateBar
        )
    }

    @Test
    fun winning_rate_bar_is_hidden_shown_when_rate_is_not_zero_but_sum_is_not_1() {
        val userInfoUi = createUserInfoUi(
            rateOfWinning = 0.1f,
            rateOfLosing = 0.5f
        )

        assertEquals(
            false,
            userInfoUi.showWinningRateBar
        )
    }

    @Test
    fun winning_rate_bar_is_hidden_when_rate_is_zero() {
        val userInfoUi = createUserInfoUi(
            rateOfWinning = 0f,
            rateOfLosing = 0f
        )

        assertEquals(
            false,
            userInfoUi.showWinningRateBar
        )
    }
}
