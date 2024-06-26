package com.rt.base.bean

data class ParkingLotResultBean(
    val result: List<ParkingLotBean>
)

data class ParkingLotBean(
    val carColor: String,
    val carLicense: String,
    val cleared: String,
    val deadLine: Long,
    val orderNo: String,
    val parkingNo: String,
    val state: String
)