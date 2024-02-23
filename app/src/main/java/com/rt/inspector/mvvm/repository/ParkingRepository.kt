package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.HttpWrapper
import com.rt.base.bean.IncomeCountingBean
import com.rt.base.bean.ParkingLotDetailBean
import com.rt.base.bean.ParkingLotResultBean
import com.rt.base.bean.ParkingManagementResultBean
import com.rt.base.bean.TrafficAssistantResultBean

class ParkingRepository : BaseRepository() {

    suspend fun parkingManagementList(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<ParkingManagementResultBean> {
        return mServer.parkingManagementList(param)
    }

    suspend fun parkingLotList(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<ParkingLotResultBean> {
        return mServer.parkingLotList(param)
    }

    suspend fun parkingLotDetail(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<ParkingLotDetailBean> {
        return mServer.parkingLotDetail(param)
    }

    suspend fun trafficAssistantList(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<TrafficAssistantResultBean> {
        return mServer.trafficAssistantList(param)
    }
    suspend fun incomeCounting(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<IncomeCountingBean> {
        return mServer.incomeCounting(param)
    }

}