package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.HttpWrapper
import com.rt.base.bean.QueryAssistantNameResultBean

class ViolationRepository : BaseRepository() {
    suspend fun queryAssistantName(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<QueryAssistantNameResultBean> {
        return mServer.queryAssistantName(param)
    }
}