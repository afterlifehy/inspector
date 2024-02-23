package com.rt.base.bean

class FeeStandardBean(
    var street: Street? = null,
    var feeStandardHighResultBean: FeeStandardHighResultBean? = null,
    var feeStandardNotHighBean: FeeStandardNotHighBean? = null,
    var isExpand: Boolean = false
) {}