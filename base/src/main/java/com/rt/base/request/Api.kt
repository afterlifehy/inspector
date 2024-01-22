package com.rt.base.request

import com.rt.base.bean.*
import retrofit2.http.*


interface Api {
    /**
     * 签到
     */
    @POST("S_G0_01")
    suspend fun login(@Body param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<Any>

}