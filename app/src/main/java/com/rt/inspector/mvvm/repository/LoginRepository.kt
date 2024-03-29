package com.rt.inspector.mvvm.repository

import com.rt.base.base.mvvm.BaseRepository
import com.rt.base.bean.HttpWrapper
import com.rt.base.bean.LoginBean
import com.rt.base.bean.UpdateBean

class LoginRepository : BaseRepository() {

    /**
     * 登录
     */
    suspend fun login(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<LoginBean> {
        return mServer.login(param)
    }

    suspend fun checkUpdate(param: @JvmSuppressWildcards Map<String, Any?>): HttpWrapper<UpdateBean> {
        return mServer.checkUpdate(param)
    }
}