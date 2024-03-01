package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.HttpWrapper
import com.rt.base.bean.TaskResultBean

class TaskRepository: BaseRepository() {

    suspend fun queryTask(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<TaskResultBean> {
        return mServer.queryTask(param)
    }
}