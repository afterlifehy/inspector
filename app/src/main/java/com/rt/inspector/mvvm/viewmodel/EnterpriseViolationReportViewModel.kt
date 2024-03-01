package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.EnterpriseResultBean
import com.rt.inspector.mvvm.repository.ViolationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EnterpriseViolationReportViewModel : BaseViewModel() {
    val mViolationRepository by lazy {
        ViolationRepository()
    }

    val queryEnterpriseListLiveData = MutableLiveData<EnterpriseResultBean>()
    val enterpriseViolationReportLiveData = MutableLiveData<Any>()

    fun queryEnterpriseList(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mViolationRepository.queryEnterpriseList(param)
            }
            executeResponse(response, {
                queryEnterpriseListLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }

    fun enterpriseViolationReport(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mViolationRepository.enterpriseViolationReport(param)
            }
            executeResponse(response, {
                enterpriseViolationReportLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}