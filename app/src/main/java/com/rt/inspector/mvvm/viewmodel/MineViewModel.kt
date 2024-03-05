package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.UpdateBean
import com.rt.inspector.mvvm.repository.MineRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MineViewModel: BaseViewModel() {
    val mMineRepository by lazy {
        MineRepository()
    }

    val checkUpdateLiveData = MutableLiveData<UpdateBean>()

    fun checkUpdate(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mMineRepository.checkUpdate(param)
            }
            executeResponse(response, {
                checkUpdateLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}