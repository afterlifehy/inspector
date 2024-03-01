package com.rt.base.bean

data class AssistantViolationHistoryResultBean(
    val result: List<AssistantViolationHistoryBean>
)

data class AssistantViolationHistoryBean(
    val adminAccount: String,
    val managerName: String,
    val mviolateId: String,
    val reportTime: String,
    val state: String,
    val streetName: String,
    val streetNo: String,
    val type: String
)