package com.rt.base.bean

data class TaskResultBean(
    val result: List<TaskBean>
)

data class TaskBean(
    val abnormalId: String,
    val notificationTitle: String,
    val receiveTaskTime: String,
    val streetNo: String,
    val taskNo: String,
    val taskSource: String,
    val taskState: String,
    val taskTitle: String
)