package com.rt.base.bean

data class FeeStandardNotHighBean(
    var count: Count,
    var streetName: String,
    var streetNo: String,
    var timing: Timing
) {
    constructor() : this(Count(emptyList(), emptyList()), "", "", Timing(emptyList(), emptyList(), emptyList()))
}

data class Count(
    var duration: List<String>,
    var money: List<String>
)

data class Timing(
    var duration: List<String>,
    var halfMoney: List<String>,
    var hourMoney: List<String>
)