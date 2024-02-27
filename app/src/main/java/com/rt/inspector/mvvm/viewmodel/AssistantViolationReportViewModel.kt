package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.QueryAssistantNameResultBean
import com.rt.inspector.mvvm.repository.ViolationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssistantViolationReportViewModel : BaseViewModel() {
    val mViolationRepository by lazy {
        ViolationRepository()
    }

    val queryAssistantNameLiveData = MutableLiveData<QueryAssistantNameResultBean>()
    fun queryAssistantName(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mViolationRepository.queryAssistantName(param)
            }
            executeResponse(response, {
                queryAssistantNameLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}