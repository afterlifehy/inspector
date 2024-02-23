package com.rt.base.bean

data class TrafficAssistantResultBean(
    val result: List<TrafficAssistantBean>
)

data class TrafficAssistantBean(
    val beginTime: String,
    val endTime: String,
    val locationState: String,
    val managerAccount: String,
    val managerName: String,
    val offState: String,
    val onState: String,
    val phone: String,
    val streetName: String
)