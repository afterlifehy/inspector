package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.ParkingLotDetailBean
import com.rt.base.bean.QueryParkingInfoResultBean
import com.rt.inspector.mvvm.repository.InfoVerifyRepository
import com.rt.inspector.mvvm.repository.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ParkingInfoViewModel: BaseViewModel() {

    val mInfoVerifyRepository by lazy {
        InfoVerifyRepository()
    }

    val queryParkingInfoLiveData = MutableLiveData<QueryParkingInfoResultBean>()
    fun queryParkingInfo(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mInfoVerifyRepository.queryParkingInfo(param)
            }
            executeResponse(response, {
                queryParkingInfoLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}