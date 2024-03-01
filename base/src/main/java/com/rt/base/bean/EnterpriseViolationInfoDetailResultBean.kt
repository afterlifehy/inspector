package com.rt.base.bean

data class EnterpriseViolationInfoDetailResultBean(
    val result: EnterpriseViolationInfoDetailBean
)

data class EnterpriseViolationInfoDetailBean(
    val comment: String,
    val companyName: String,
    val contentList: List<String>,
    val reportTime: String,
    val state: String,
    val type: String
)