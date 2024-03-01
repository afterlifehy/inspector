package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.AssistantViolationInfoDetailResultBean
import com.rt.inspector.mvvm.repository.ViolationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AssistantViolationDetailViewModel: BaseViewModel() {
    val mViolationRepository by lazy {
        ViolationRepository()
    }

    val assistantViolationInfoDetailLiveData = MutableLiveData<AssistantViolationInfoDetailResultBean>()

    fun assistantViolationInfoDetail(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mViolationRepository.assistantViolationInfoDetail(param)
            }
            executeResponse(response, {
                assistantViolationInfoDetailLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}