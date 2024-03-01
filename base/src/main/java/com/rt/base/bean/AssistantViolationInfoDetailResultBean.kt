package com.rt.base.bean

data class AssistantViolationInfoDetailResultBean(
    val result: AssistantViolationInfoDetailBean
)

data class AssistantViolationInfoDetailBean(
    val adminAccount: String,
    val comment: String,
    val contentList: List<String>,
    val managerName: String,
    val reportTime: String,
    val state: String,
    val streetName: String,
    val streetNo: String,
    val type: String
)