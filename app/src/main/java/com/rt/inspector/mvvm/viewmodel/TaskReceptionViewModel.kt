package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.TaskResultBean
import com.rt.inspector.mvvm.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskReceptionViewModel: BaseViewModel() {

    val mTaskRepository by lazy {
        TaskRepository()
    }

    val queryTaskLiveData = MutableLiveData<TaskResultBean>()

    fun queryTask(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mTaskRepository.queryTask(param)
            }
            executeResponse(response, {
                queryTaskLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}