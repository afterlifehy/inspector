package com.rt.base.bean

data class TaskDetailBean(
    val abnormalDesc: String,
    val abnormalPhotoPath: List<String>,
    val name: String,
    val phone: String,
    val remark: String,
    val reportTime: String,
    val taskNo: String,
    val taskSource: String
)