package com.rt.inspector.mvvm.viewmodel

import com.rt.base.base.mvvm.BaseViewModel
import com.rt.inspector.mvvm.repository.ViolationRepository

class EnterpriseViolationReportViewModel : BaseViewModel() {
    val mViolationRepository by lazy {
        ViolationRepository()
    }
}