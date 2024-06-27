package com.yessorae.domain.model

import com.yessorae.domain.entity.User
import com.yessorae.domain.entity.value.Money
import junit.framework.Assert.assertEquals
import org.junit.Test

class UserTest {
    @Test
    fun initial_user_winning_rate_is_zero() {
        // createInitialUser는 운영코드에서 사용되는 함수
        val sut: User = User.createInitialUser()

        assertEquals(0.0, sut.rateOfWinning)
    }

    @Test
    fun initial_user_losing_rate_is_zero() {
        // createInitialUser는 운영코드에서 사용되는 함수
        val sut: User = User.createInitialUser()

        assertEquals(0.0, sut.rateOfLosing)
    }

    @Test
    fun user_winning_rate() {
        val sut: User = createTestUser(
            winCount = 3,
            loseCount = 1
        )

        assertEquals(0.75, sut.rateOfWinning)
    }

    @Test
    fun user_losing_rate() {
        val sut: User = createTestUser(
            winCount = 1,
            loseCount = 3
        )

        assertEquals(0.75, sut.rateOfLosing)
    }

    @Test
    fun user_quite_game_result() {
        val sut: User = createTestUser(
            winCount = 1,
            loseCount = 2
        )

        val result: User = sut.quiteGame()

        assertEquals(
            sut.copy(
                loseCount = 3
            ),
            result
        )
    }

    @Test
    fun user_finish_game_with_wining_result() {
        val sut: User = createTestUser(
            balance = Money.of(100_000.0),
            winCount = 98,
            loseCount = 1,
            averageRateOfProfit = 2.0
        )

        val result: User = sut.finishGame(
            profit = 100.0,
            rateOfProfit = 1.0
        )

        assertEquals(
            sut.copy(
                balance = Money.of(100_100.0),
                winCount = 99,
                averageRateOfProfit = 1.99
            ),
            result
        )
    }

    @Test
    fun user_finish_game_with_losing_result() {
        val sut: User = createTestUser(
            balance = Money.of(100_000.0),
            winCount = 98,
            loseCount = 1,
            averageRateOfProfit = 2.0
        )

        val result: User = sut.finishGame(
            profit = -100.0,
            rateOfProfit = -1.0
        )

        assertEquals(
            sut.copy(
                balance = Money.of(99_900.0),
                loseCount = 2,
                averageRateOfProfit = 1.97
            ),
            result
        )
    }
}
