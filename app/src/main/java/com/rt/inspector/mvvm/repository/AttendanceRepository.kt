package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.HttpWrapper
import com.rt.base.bean.WorkAttendanceRecordBean

class AttendanceRepository : BaseRepository() {
    suspend fun workAttendanceRecord(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<WorkAttendanceRecordBean> {
        return mServer.workAttendanceRecord(param)
    }

    suspend fun clockInOut(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any> {
        return mServer.clockInOut(param)
    }
}