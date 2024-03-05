package com.rt.base.bean

data class BindRoadInfoResultBean(
    val result: List<Road>
)

data class Road(
    val streetName: String,
    val streetNo: String
)