package com.yessorae.data.source.local.database.dao

import androidx.room.Dao
import com.yessorae.data.source.local.database.model.TickTable

@Dao
interface TickDao : BaseDao<TickTable>
