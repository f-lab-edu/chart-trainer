package com.yessorae.data.source.local.database.converter

import androidx.room.TypeConverter
import com.yessorae.domain.entity.value.Money

class MoneyConverter {
    @TypeConverter
    fun valueToMoney(value: Double?): Money? = value?.let { Money(it) }

    @TypeConverter
    fun moneyToValue(money: Money?): Double? = money?.value
}
