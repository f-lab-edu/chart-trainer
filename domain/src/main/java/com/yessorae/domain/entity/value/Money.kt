package com.yessorae.domain.entity.value

import kotlinx.serialization.Serializable

@Serializable
data class Money(
    val value: Double
) {
    // TODO::LATER 화폐단위 바꿔주는 함수 추가

    operator fun plus(other: Money): Money {
        return Money(value + other.value)
    }

    operator fun times(other: Money): Money {
        return Money(value * other.value)
    }

    operator fun times(count: Int): Money {
        return Money(value * count)
    }

    operator fun times(count: Double): Money {
        return Money(value * count)
    }

    operator fun minus(other: Money): Money {
        return Money(value - other.value)
    }

    operator fun div(other: Money): Money {
        return Money(value / other.value)
    }

    operator fun div(other: Double): Money {
        return Money(value / other)
    }

    operator fun div(other: Int): Money {
        return Money(value / other)
    }

    companion object {
        val ZERO = Money(0.0)
        fun of(value: Double): Money {
            return Money(value)
        }
    }
}
