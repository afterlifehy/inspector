package com.rt.base.bean

data class BusinessLicenseResultBean(
    val result: List<BusinessLicenseBean>
)

data class BusinessLicenseBean(
    val authentication: String,
    val companyName: String,
    val expirationTime: String,
    val streetName: String,
    val streetNo: String
)