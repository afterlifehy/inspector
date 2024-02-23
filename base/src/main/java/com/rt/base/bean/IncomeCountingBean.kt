package com.rt.base.bean

data class IncomeCountingBean(
    val autonomousPayCount: String,
    val beRecoveredMoney: String,
    val orderTotal: Int,
    val partPayCount: Int,
    val refusePayCount: Int,
    val totalAmounts: String,
    val totalMoney: String,
    val totalNotBerecoveredMoney: String,
    val totalRecoveredMoney: String
)