package com.rt.base.request

import com.rt.base.bean.*
import retrofit2.http.*


interface Api {
    /**
     * 签到
     */
    @POST("S_VO3_01")
    suspend fun login(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<LoginBean>

    /**
     * 获取路段信息
     */
    @POST("S_VO3_03")
    suspend fun parkingManagementList(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<ParkingManagementResultBean>

    /**
     * 获取泊位列表
     */
    @POST("S_VO3_04")
    suspend fun parkingLotList(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<ParkingLotResultBean>

    /**
     * 获取泊位订单详情
     */
    @POST("S_VO3_05")
    suspend fun parkingLotDetail(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<ParkingLotDetailBean>
}