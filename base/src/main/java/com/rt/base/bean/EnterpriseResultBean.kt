package com.rt.base.bean

data class EnterpriseResultBean(
    val result: List<EnterpriseBean>
)

data class EnterpriseBean(
    val companyId: String,
    val companyName: String
)