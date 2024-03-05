package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.TaskDetailBean
import com.rt.inspector.mvvm.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AbnormalDetailViewModel : BaseViewModel() {
    val mTaskRepository by lazy {
        TaskRepository()
    }

    val queryTaskDetailLiveData = MutableLiveData<TaskDetailBean>()

    fun queryTaskDetail(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mTaskRepository.queryTaskDetail(param)
            }
            executeResponse(response, {
                queryTaskDetailLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}