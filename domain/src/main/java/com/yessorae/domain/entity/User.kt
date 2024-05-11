package com.yessorae.domain.entity

import com.yessorae.domain.entity.value.Money

data class User(
    val balance: Money,
    val winCount: Int, // 승리 횟수
    val loseCount: Int, //
    val averageRateOfReturn: Double
//    아래 프로퍼티는 로그인 피쳐할 때 추가
//    val isAnonnymous: Boolean,
//    val profileImg: String?,
//    val nickname: String,
)