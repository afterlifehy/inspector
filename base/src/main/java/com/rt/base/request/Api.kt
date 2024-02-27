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

    /**
     * 获取当班协管员信息
     */
    @POST("S_VO3_06")
    suspend fun trafficAssistantList(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<TrafficAssistantResultBean>

    /**
     * 营收盘点
     */
    @POST("S_VO3_07")
    suspend fun incomeCounting(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<IncomeCountingBean>

    /**
     * 查询停车路段经营许可信息
     */
    @POST("S_VO3_08")
    suspend fun businessLicenseList(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<BusinessLicenseResultBean>

    /**
     * 收费标准(非高位路段)
     */
    @POST("S_VO3_10")
    suspend fun feeStandardNotHigh(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<FeeStandardNotHighBean>

    /**
     * 收费标准(高位路段)
     */
    @POST("S_VO2_18")
    suspend fun feeStandardHigh(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<FeeStandardHighResultBean>

    /**
     * 查询停车人信息
     */
    @POST("S_VO3_11")
    suspend fun queryParkingInfo(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<QueryParkingInfoResultBean>

    /**
     * 查询协管员姓名
     */
    @POST("S_VO3_13")
    suspend fun queryAssistantName(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<QueryAssistantNameResultBean>
}