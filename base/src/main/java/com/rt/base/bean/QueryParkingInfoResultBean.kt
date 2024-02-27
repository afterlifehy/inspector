package com.rt.base.bean

data class QueryParkingInfoResultBean(
    val result: List<QueryParkingInfoBean>
)

data class QueryParkingInfoBean(
    val carLicense: String,
    val driverName: String,
    val phone: String,
    val endTime: String,
    val isUser: String,
    val startTime: String
)