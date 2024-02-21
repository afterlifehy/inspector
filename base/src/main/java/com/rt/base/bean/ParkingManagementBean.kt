package com.rt.base.bean

data class ParkingManagementResultBean(
    val result: List<ParkingManagementBean>
)

data class ParkingManagementBean(
    val actualAmount: String,
    val adminNames: List<String>,
    val orderAmount: String,
    val streetName: String,
    val streetNo: String
)