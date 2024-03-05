package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.base.bean.WorkAttendanceRecordBean
import com.rt.inspector.mvvm.repository.AttendanceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkAttendanceViewModel : BaseViewModel() {

    val mAttendanceRepository by lazy {
        AttendanceRepository()
    }

    val workAttendanceRecordLiveData = MutableLiveData<WorkAttendanceRecordBean>()
    val clockInOutLiveData = MutableLiveData<Any>()

    fun workAttendanceRecord(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mAttendanceRepository.workAttendanceRecord(param)
            }
            executeResponse(response, {
                workAttendanceRecordLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }

    fun clockInOut(param: Map<String, Any?>) {
        launch {
            val response = withContext(Dispatchers.IO) {
                mAttendanceRepository.clockInOut(param)
            }
            executeResponse(response, {
                clockInOutLiveData.value = response.attr
            }, {
                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
            })
        }
    }
}