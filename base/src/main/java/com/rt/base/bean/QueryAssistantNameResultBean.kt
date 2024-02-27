package com.rt.base.bean

data class QueryAssistantNameResultBean(
    val result: QueryAssistantNameBean
)

data class QueryAssistantNameBean(
    val adminName: String,
    val managerAccount: String,
    val phone: String
)