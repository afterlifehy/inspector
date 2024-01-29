package com.rt.inspector.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rt.base.base.mvvm.BaseViewModel
import com.rt.base.base.mvvm.ErrorMessage
import com.rt.inspector.mvvm.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel: BaseViewModel() {

    val mLoginRepository by lazy {
        LoginRepository()
    }

    val loginLiveData = MutableLiveData<Any>()

    fun login(param: Map<String, Any?>) {
        loginLiveData.value = true
//        launch {
//            val response = withContext(Dispatchers.IO) {
//                mLoginRepository.login(param)
//            }
//            executeResponse(response, {
//                loginLiveData.value = response.attr
//            }, {
//                traverseErrorMsg(ErrorMessage(msg = response.msg, code = response.status))
//            })
//        }
    }
}