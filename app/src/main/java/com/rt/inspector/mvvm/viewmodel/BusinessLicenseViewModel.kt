package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.BusinessLicenseResultBean
import com.rt.inspector.mvvm.repository.InfoVerifyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BusinessLicenseViewModel: BaseViewModel() {

    val mInfoVerifyRepository by lazy {
        InfoVerifyRepository()
    }

    val businessLicenseListLiveData = MutableLiveData<BusinessLicenseResultBean>()

    fun businessLicenseList(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mInfoVerifyRepository.businessLicenseList(param)
            }
            executeResponse(response, {
                businessLicenseListLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}