package com.rt.inspector.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.baidu.location.LocationClientOption
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.PermissionUtils.FullCallback
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18N
import com.rt.base.ext.startArouter
import com.rt.base.help.ActivityCacheManager
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.AppUtil
import com.rt.common.util.BaiduLocationUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityMainBinding
import com.rt.inspector.mvvm.viewmodel.MainViewModel
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.MAIN)
class MainActivity : VbBaseActivity<MainViewModel, ActivityMainBinding>(), OnClickListener {
    var rxPermissions = RxPermissions(this@MainActivity)
    lateinit var baiduLocationUtil: BaiduLocationUtil
    var lat = 121.445345
    var lon = 31.238665
    var locationEnable = 0
    var loginName = ""

    @SuppressLint("CheckResult", "MissingPermission")
    override fun initView() {
        repeatCheckLocation {
            runOnUiThread {
                PermissionUtils.permission(Manifest.permission.ACCESS_FINE_LOCATION).callback(object : FullCallback {
                    override fun onGranted(granted: MutableList<String>) {
                        baiduLocationUtil = BaiduLocationUtil()
                        baiduLocationUtil.initBaiduLocation()
                        val callback = object : BaiduLocationUtil.BaiduLocationCallBack {
                            override fun locationChange(
                                lon: Double,
                                lat: Double,
                                location: LocationClientOption?,
                                isSuccess: Boolean,
                                address: String?
                            ) {
                                if (isSuccess) {
                                    this@MainActivity.lat = lat
                                    this@MainActivity.lon = lon
                                    locationEnable = 1
                                } else {
                                    locationEnable = -1
                                }
                            }

                        }
                        baiduLocationUtil.setBaiduLocationCallBack(callback)
                        baiduLocationUtil.startLocation()
                        if (locationEnable != -1) {
                            runBlocking {
                                loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
                                if (loginName.isNotEmpty()) {
                                    val param = HashMap<String, Any>()
                                    val jsonobject = JSONObject()
                                    jsonobject["loginName"] = loginName
                                    jsonobject["longitude"] = lon
                                    jsonobject["latitude"] = lat
                                    param["attr"] = jsonobject
                                    mViewModel.locationUpload(param)
                                }
                            }
                        } else {
                            ToastUtil.showMiddleToast("Provider Disabled")
                        }
                    }

                    override fun onDenied(deniedForever: MutableList<String>, denied: MutableList<String>) {

                    }

                }).request()
            }
        }
    }

    fun repeatCheckLocation(action: () -> Unit) {
        runBlocking {
            PreferencesDataStore(BaseApplication.instance()).putBoolean(PreferencesKeys.isUpdateLocation, true)
            GlobalScope.launch {
                while (PreferencesDataStore(BaseApplication.instance()).getBoolean(PreferencesKeys.isUpdateLocation)) {
                    delay(1000 * 60 * 5)
                    action.invoke()
                }
            }
        }
    }

    override fun initListener() {
        binding.ivHead.setOnClickListener(this)
        binding.flParkingManagement.setOnClickListener(this)
        binding.flViolationReport.setOnClickListener(this)
        binding.flAttendanceManagement.setOnClickListener(this)
        binding.flRoadBind.setOnClickListener(this)
        binding.flTaskReception.setOnClickListener(this)
        binding.flInfoVerification.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_head -> {
                startArouter(ARouterMap.MINE)
            }

            R.id.fl_parkingManagement -> {
                startArouter(ARouterMap.PARKING_MANAGEMENT)
            }

            R.id.fl_violationReport -> {
                startArouter(ARouterMap.VIOLATION_REPORT_MAIN)
            }

            R.id.fl_attendanceManagement -> {
                startArouter(ARouterMap.WORK_ATTENDANCE)
            }

            R.id.fl_roadBind -> {
                startArouter(ARouterMap.ROAD_BIND)
            }

            R.id.fl_taskReception -> {
                startArouter(ARouterMap.TASK_RECEPTION)
            }

            R.id.fl_info_verification -> {
                startArouter(ARouterMap.INFO_VERIFY_MAIN)
            }
        }
    }

    override fun providerVMClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = false

    override fun onBackPressedSupport() {
        if (AppUtil.isFastClick(1000)) {
            ActivityCacheManager.instance().getAllActivity().forEach {
                if (!it.isFinishing) {
                    it.finish()
                }
            }
        } else {
            ToastUtil.showMiddleToast(i18N(com.rt.base.R.string.再按一次退出程序))
        }
    }

}