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
     * 稽查员轨迹上传
     */
    @POST("S_VO3_02")
    suspend fun locationUpload(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any>

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

    /**
     * 协管员违规上报
     */
    @POST("S_VO3_14")
    suspend fun assistantViolationReport(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any>

    /**
     * 协管员违规历史查询
     */
    @POST("S_VO3_15")
    suspend fun queryAssistantViolationHistory(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<AssistantViolationHistoryResultBean>

    /**
     * 协管员违规信息详情
     */
    @POST("S_VO3_24")
    suspend fun assistantViolationInfoDetail(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<AssistantViolationInfoDetailResultBean>

    /**
     * 企业列表
     */
    @POST("S_VO3_25")
    suspend fun queryEnterpriseList(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<EnterpriseResultBean>

    /**
     * 企业违规上报
     */
    @POST("S_VO3_26")
    suspend fun enterpriseViolationReport(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any>

    /**
     * 企业违规历史查询
     */
    @POST("S_VO3_27")
    suspend fun queryEnterpriseViolationHistory(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<EnterpriseViolationHistoryResultBean>

    /**
     * 企业违规详情
     */
    @POST("S_VO3_28")
    suspend fun enterpriseViolationInfoDetail(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<EnterpriseViolationInfoDetailResultBean>

    /**
     * 查询任务
     */
    @POST("S_VO3_16")
    suspend fun queryTask(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<TaskResultBean>

    /**
     * 查看任务详情
     */
    @POST("S_VO3_17")
    suspend fun queryTaskDetail(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<TaskDetailBean>

    /**
     * 获取考勤记录
     */
    @POST("S_VO3_19")
    suspend fun workAttendanceRecord(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<WorkAttendanceRecordBean>

    /**
     * 考勤打卡
     */
    @POST("S_VO3_20")
    suspend fun clockInOut(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any>

    /**
     * 获取协管员绑定的路段信息
     */
    @POST("S_VO3_21")
    suspend fun getBindRoadInfo(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<BindRoadInfoResultBean>

    /**
     * 给协管员账号注册路段
     */
    @POST("S_VO3_22")
    suspend fun bindRoad(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any>

    /**
     * 检查最新版本
     */
    @POST("S_VO3_23")
    suspend fun checkUpdate(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<UpdateBean>
}