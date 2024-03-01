package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.AssistantViolationHistoryResultBean
import com.rt.inspector.mvvm.repository.ViolationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssistantViolationHistoryViewModel: BaseViewModel() {

    val mViolationRepository by lazy {
        ViolationRepository()
    }

    val queryAssistantViolationHistoryLiveData = MutableLiveData<AssistantViolationHistoryResultBean>()

    fun queryAssistantViolationHistory(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mViolationRepository.queryAssistantViolationHistory(param)
            }
            executeResponse(response, {
                queryAssistantViolationHistoryLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}