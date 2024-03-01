package com.rt.base.bean

data class EnterpriseViolationHistoryResultBean(
    val result: List<EnterpriseViolationHistoryBean>
)

data class EnterpriseViolationHistoryBean(
    val companyName: String,
    val mviolateId: String,
    val reportTime: String,
    val state: String,
    val type: String
)