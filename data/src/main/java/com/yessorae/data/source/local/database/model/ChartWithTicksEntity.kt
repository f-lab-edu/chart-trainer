package com.yessorae.data.source.local.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.yessorae.domain.entity.Chart

data class ChartWithTicksEntity(
    @Embedded
    val chart: ChartEntity,
    @Relation(
        parentColumn = ChartEntity.COL_ID,
        entityColumn = TickEntity.COL_CHART_ID
    )
    val ticks: List<TickEntity>
)

fun ChartWithTicksEntity.asDomainModel() =
    Chart(
        id = chart.id,
        tickerSymbol = chart.tickerSymbol,
        startDateTime = chart.startDateTime,
        endDateTime = chart.endDateTime,
        tickUnit = chart.tickUnit,
        ticks = ticks.map { it.asDomainModel() }
    )
