package com.rt.base.bean

data class FeeStandardHighResultBean(
    var result: List<FeeStandardHighBean>
) {
    constructor() : this(emptyList())
}

data class FeeStandardHighBean(
    var blackEnd: String,
    var blackStart: String,
    var dateType: Int,
    var first: String,
    var period: String,
    var second: String,
    var third: String,
    var unitPrice: String,
    var whiteEnd: String,
    var whiteStart: String
)