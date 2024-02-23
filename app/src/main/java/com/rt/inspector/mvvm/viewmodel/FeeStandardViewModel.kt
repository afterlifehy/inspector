package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.FeeStandardHighResultBean
import com.rt.base.bean.FeeStandardNotHighBean
import com.rt.inspector.mvvm.repository.InfoVerifyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeeStandardViewModel : BaseViewModel() {

    val mInfoVerifyRepository by lazy {
        InfoVerifyRepository()
    }

    val feeStandardNotHighLiveData = MutableLiveData<FeeStandardNotHighBean>()
    val feeStandardHighLiveData = MutableLiveData<FeeStandardHighResultBean>()

    fun feeStandardNotHigh(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mInfoVerifyRepository.feeStandardNotHigh(param)
            }
            executeResponse(response, {
                feeStandardNotHighLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }

    fun feeStandardHigh(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mInfoVerifyRepository.feeStandardHigh(param)
            }
            executeResponse(response, {
                feeStandardHighLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}