package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.BindRoadInfoResultBean
import com.rt.inspector.mvvm.repository.RoadBindRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoadBindViewModel : BaseViewModel() {

    val mRoadBindRepository by lazy {
        RoadBindRepository()
    }

    val getBindRoadInfoLiveData = MutableLiveData<BindRoadInfoResultBean>()
    val bindRoadLiveData = MutableLiveData<Any>()
    val unbindRoadLiveData = MutableLiveData<Any>()

    fun getBindRoadInfo(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mRoadBindRepository.getBindRoadInfo(param)
            }
            executeResponse(response, {
                getBindRoadInfoLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }

    fun bindRoad(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mRoadBindRepository.bindRoad(param)
            }
            executeResponse(response, {
                bindRoadLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }

    fun unbindRoad(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mRoadBindRepository.unbindRoad(param)
            }
            executeResponse(response, {
                unbindRoadLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}