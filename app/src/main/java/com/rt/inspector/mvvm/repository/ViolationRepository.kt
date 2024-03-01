package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.AssistantViolationHistoryResultBean
import com.rt.base.bean.AssistantViolationInfoDetailResultBean
import com.rt.base.bean.EnterpriseResultBean
import com.rt.base.bean.EnterpriseViolationHistoryBean
import com.rt.base.bean.EnterpriseViolationHistoryResultBean
import com.rt.base.bean.EnterpriseViolationInfoDetailResultBean
import com.rt.base.bean.HttpWrapper
import com.rt.base.bean.QueryAssistantNameResultBean

class ViolationRepository : BaseRepository() {
    suspend fun queryAssistantName(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<QueryAssistantNameResultBean> {
        return mServer.queryAssistantName(param)
    }

    suspend fun assistantViolationReport(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any> {
        return mServer.assistantViolationReport(param)
    }

    suspend fun queryAssistantViolationHistory(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<AssistantViolationHistoryResultBean> {
        return mServer.queryAssistantViolationHistory(param)
    }

    suspend fun assistantViolationInfoDetail(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<AssistantViolationInfoDetailResultBean> {
        return mServer.assistantViolationInfoDetail(param)
    }

    suspend fun queryEnterpriseList(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<EnterpriseResultBean> {
        return mServer.queryEnterpriseList(param)
    }

    suspend fun enterpriseViolationReport(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any> {
        return mServer.enterpriseViolationReport(param)
    }

    suspend fun queryEnterpriseViolationHistory(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<EnterpriseViolationHistoryResultBean> {
        return mServer.queryEnterpriseViolationHistory(param)
    }

    suspend fun enterpriseViolationInfoDetail(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<EnterpriseViolationInfoDetailResultBean> {
        return mServer.enterpriseViolationInfoDetail(param)
    }
}