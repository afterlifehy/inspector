package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.EnterpriseViolationInfoDetailResultBean
import com.rt.inspector.mvvm.repository.ViolationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EnterpriseViolationDetailViewModel: BaseViewModel() {
    val mViolationRepository by lazy {
        ViolationRepository()
    }

    val enterpriseViolationInfoDetailLiveData = MutableLiveData<EnterpriseViolationInfoDetailResultBean>()

    fun enterpriseViolationInfoDetail(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mViolationRepository.enterpriseViolationInfoDetail(param)
            }
            executeResponse(response, {
                enterpriseViolationInfoDetailLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}