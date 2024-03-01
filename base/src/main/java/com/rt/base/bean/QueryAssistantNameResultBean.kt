package com.rt.base.bean

data class QueryAssistantNameResultBean(
    val result: List<QueryAssistantNameBean>
)

data class QueryAssistantNameBean(
    val adminName: String,
    val managerAccount: String,
    val phone: String
)