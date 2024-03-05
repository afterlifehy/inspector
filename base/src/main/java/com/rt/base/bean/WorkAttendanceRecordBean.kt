package com.rt.base.bean

data class WorkAttendanceRecordBean(
    val locationState: String,
    val offState: String,
    val offTime: String,
    val onState: String,
    val onTime: String,
    val signState: String
)