package com.yessorae.domain.model

import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.value.Money
import junit.framework.Assert.assertEquals
import org.junit.Test

class UserTest {
    @Test
    fun initial_user_winning_rate_is_zero() {
        // createInitialUser는 운영코드에서 사용되는 함수
        val sut = User.createInitialUser()

        assertEquals(0.0, sut.rateOfWinning)
    }

    @Test
    fun initial_user_losing_rate_is_zero() {
        // createInitialUser는 운영코드에서 사용되는 함수
        val sut = User.createInitialUser()

        assertEquals(0.0, sut.rateOfLosing)
    }

    @Test
    fun user_winning_rate() {
        val sut = baseTestUser.copy(
            winCount = 3,
            loseCount = 1
        )

        assertEquals(0.75, sut.rateOfWinning)
    }

    @Test
    fun user_losing_rate() {
        val sut = baseTestUser.copy(
            winCount = 1,
            loseCount = 3
        )

        assertEquals(0.75, sut.rateOfLosing)
    }

    @Test
    fun user_quite_game_result() {
        val sut = baseTestUser.copy(
            winCount = 1,
            loseCount = 2
        )

        val result = sut.quiteGame()

        assertEquals(
            sut.copy(
                loseCount = 3
            ),
            result
        )
    }
}