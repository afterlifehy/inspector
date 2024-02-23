package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.ParkingManagementResultBean
import com.rt.base.bean.TrafficAssistantResultBean
import com.rt.inspector.mvvm.repository.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrafficAssistantListViewModel : BaseViewModel() {
    val mParkingRepository by lazy {
        ParkingRepository()
    }

    val trafficAssistantListLiveData = MutableLiveData<TrafficAssistantResultBean>()

    fun trafficAssistantList(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mParkingRepository.trafficAssistantList(param)
            }
            executeResponse(response, {
                trafficAssistantListLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}