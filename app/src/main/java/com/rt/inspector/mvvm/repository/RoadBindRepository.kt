package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.BindRoadInfoResultBean
import com.rt.base.bean.HttpWrapper

class RoadBindRepository : BaseRepository() {
    suspend fun getBindRoadInfo(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<BindRoadInfoResultBean> {
        return mServer.getBindRoadInfo(param)
    }

    suspend fun bindRoad(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any> {
        return mServer.bindRoad(param)
    }
}