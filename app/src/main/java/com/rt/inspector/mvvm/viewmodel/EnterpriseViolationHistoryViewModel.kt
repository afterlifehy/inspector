package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.EnterpriseViolationHistoryResultBean
import com.rt.inspector.mvvm.repository.ViolationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EnterpriseViolationHistoryViewModel: BaseViewModel() {

    val mViolationRepository by lazy {
        ViolationRepository()
    }

    val queryEnterpriseViolationHistoryLiveData = MutableLiveData<EnterpriseViolationHistoryResultBean>()

    fun queryEnterpriseViolationHistory(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mViolationRepository.queryEnterpriseViolationHistory(param)
            }
            executeResponse(response, {
                queryEnterpriseViolationHistoryLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}