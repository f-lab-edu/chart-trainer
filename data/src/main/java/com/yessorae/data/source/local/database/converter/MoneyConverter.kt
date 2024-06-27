package com.yessorae.data.source.local.database.converter

import androidx.room.TypeConverter
import com.yessorae.domain.entity.value.Money
import com.yessorae.domain.entity.value.asMoney

class MoneyConverter {
    @TypeConverter
    fun valueToMoney(value: Double?): Money? = value?.let { it.asMoney() }

    @TypeConverter
    fun moneyToValue(money: Money?): Double? = money?.value
}
