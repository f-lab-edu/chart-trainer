package com.yessorae.domain.entity.value

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
}
