package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.BusinessLicenseResultBean
import com.rt.base.bean.FeeStandardHighResultBean
import com.rt.base.bean.FeeStandardNotHighBean
import com.rt.base.bean.HttpWrapper

class InfoVerifyRepository : BaseRepository() {
    suspend fun businessLicenseList(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<BusinessLicenseResultBean> {
        return mServer.businessLicenseList(param)
    }

    suspend fun feeStandardNotHigh(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<FeeStandardNotHighBean> {
        return mServer.feeStandardNotHigh(param)
    }

    suspend fun feeStandardHigh(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<FeeStandardHighResultBean> {
        return mServer.feeStandardHigh(param)
    }
}