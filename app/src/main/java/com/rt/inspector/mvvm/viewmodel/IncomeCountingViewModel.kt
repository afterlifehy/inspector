package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.IncomeCountingBean
import com.rt.base.bean.ParkingManagementResultBean
import com.rt.inspector.mvvm.repository.ParkingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class IncomeCountingViewModel : BaseViewModel() {
    val mParkingRepository by lazy {
        ParkingRepository()
    }

    val incomeCountingLiveData = MutableLiveData<IncomeCountingBean>()

    fun incomeCounting(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mParkingRepository.incomeCounting(param)
            }
            executeResponse(response, {
                incomeCountingLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}