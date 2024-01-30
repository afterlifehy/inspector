package com.rt.inspector.mvvm.viewmodel

import com.rt.base.base.mvvm.BaseViewModel
import com.rt.inspector.mvvm.repository.ParkingRepository

class ParkingManagementViewModel : BaseViewModel() {
    val mParkingRepository by lazy {
        ParkingRepository()
    }
}