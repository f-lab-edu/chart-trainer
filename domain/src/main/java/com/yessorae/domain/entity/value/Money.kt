package com.yessorae.domain.entity.value

import kotlinx.serialization.Serializable

@Serializable
data class Money(
    val value: Double
) {
    // TODO::LATER 화폐단위 바꿔주는 함수 추가

    operator fun plus(other: Money): Money {
        return (value + other.value).asMoney()
    }

    operator fun times(other: Money): Money {
        return (value * other.value).asMoney()
    }

    operator fun times(count: Int): Money {
        return (value * count).asMoney()
    }

    operator fun times(count: Double): Money {
        return (value * count).asMoney()
    }

    operator fun minus(other: Money): Money {
        return (value - other.value).asMoney()
    }

    operator fun div(other: Money): Money {
        return (value / other.value).asMoney()
    }

    operator fun div(other: Double): Money {
        return (value / other).asMoney()
    }

    operator fun div(other: Int): Money {
        return (value / other).asMoney()
    }

    companion object {
        val ZERO = 0.asMoney()
        fun of(value: Double): Money {
            return Money(value)
        }
    }
}

fun Int.asMoney() = Money.of(value = this.toDouble())

fun Float.asMoney() = Money.of(value = this.toDouble())

fun Double.asMoney() = Money.of(value = this)
