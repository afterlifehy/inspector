package com.rt.inspector.ui.activity.attendance

import android.Manifest
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.blankj.utilcode.util.TimeUtils
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.gone
import com.rt.base.ext.i18N
import com.rt.base.ext.i18n
import com.rt.base.ext.show
import com.rt.base.ext.startArouter
import com.rt.base.help.ActivityCacheManager
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityWorkAttendanceBinding
import com.rt.inspector.mvvm.viewmodel.WorkAttendanceViewModel
import com.rt.inspector.ui.activity.login.LoginActivity
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Route(path = ARouterMap.WORK_ATTENDANCE)
class WorkAttendanceActivity : VbBaseActivity<WorkAttendanceViewModel, ActivityWorkAttendanceBinding>(), OnClickListener {
    var rxPermissions = RxPermissions(this@WorkAttendanceActivity)
    var locationManager: LocationManager? = null
    var lat = 121.445345
    var lon = 31.238665
    var locationEnable = 0
    private var job: Job? = null
    var signState = "01" //01未签到 02未签退 03已签退
    var loginName = ""
    var clockTime = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.考勤)
        job = GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                val time = TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm:ss")
                withContext(Dispatchers.Main) {
                    binding.rtvHour1.text = time.split(":")[0][0].toString()
                    binding.rtvHour2.text = time.split(":")[0][1].toString()
                    binding.rtvMinute1.text = time.split(":")[1][0].toString()
                    binding.rtvMinute2.text = time.split(":")[1][1].toString()
                    binding.rtvSec1.text = time.split(":")[2][0].toString()
                    binding.rtvSec2.text = time.split(":")[2][1].toString()
                }
                delay(1000)
            }
        }
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvLogInOut.setOnClickListener(this)
    }

    override fun initData() {
        showProgressDialog(5000)
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            val param = HashMap<String, Any>()
            val jsonobject = JSONObject()
            jsonobject["loginName"] = loginName
            param["attr"] = jsonobject
            mViewModel.workAttendanceRecord(param)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_logInOut -> {
                when (signState) {
                    "01" -> {
                        clockTime = TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm")
                        clockInOut()
                    }

                    "02" -> {
                        clockTime = TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm")
                        clockInOut()
                    }
                }
            }
        }
    }

    fun clockInOut() {
        rxPermissions.request(
            Manifest.permission.ACCESS_FINE_LOCATION
        ).subscribe {
            if (it) {
                locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val provider = LocationManager.NETWORK_PROVIDER
                locationManager?.requestLocationUpdates(provider, 1000, 1f, object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        lat = location.latitude
                        lon = location.longitude
                        locationEnable = 1
                    }

                    override fun onProviderDisabled(provider: String) {
                        locationEnable = -1
                        ToastUtil.showMiddleToast(i18N(com.rt.base.R.string.定位不可用))
                    }

                    override fun onProviderEnabled(provider: String) {
                        locationEnable = 1
                    }
                })
                if (locationEnable != -1) {
                    val param = HashMap<String, Any>()
                    val jsonobject = JSONObject()
                    jsonobject["loginName"] = loginName
                    jsonobject["signTime"] = clockTime
                    jsonobject["longitude"] = lon
                    jsonobject["latitude"] = lat
                    jsonobject["signState"] = signState
                    param["attr"] = jsonobject
                    mViewModel.clockInOut(param)
                }
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            clockInOutLiveData.observe(this@WorkAttendanceActivity) {
                dismissProgressDialog()
                when (signState) {
                    "01" -> {
                        binding.tvLogInOut.text = i18n(com.rt.base.R.string.下班打卡)
                        binding.tvOnWorkTime.text = clockTime
                        signState = "02"

                        binding.tvLogInOut.text = i18n(com.rt.base.R.string.下班打卡)
                        binding.tvOnWorkTime.text = clockTime
                        binding.rtvOnWorkRangeStatus.show()
                        binding.rtvOffWorkStatus.show()
                        binding.rtvOffWorkRangeStatus.gone()
//                        if (it.onState == "00") {
//                            binding.rtvOnWorkStatus.text = i18n(com.rt.base.R.string.正常)
//                        } else if (it.onState == "01") {
//                            binding.rtvOnWorkStatus.text = i18n(com.rt.base.R.string.迟到)
//                        }
//                        if (it.locationState == "00") {
//                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
//                        } else if (it.onState == "01") {
//                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
//                        } else if (it.onState == "02") {
//                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
//                        } else if (it.onState == "03") {
//                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
//                        }
                        binding.rtvOffWorkStatus.text = i18n(com.rt.base.R.string.未签退)
                    }

                    "02" -> {
                        runBlocking {
                            PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.name, "")
                            PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.department, "")
                            PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.phone, "")
                        }
                        startArouter(ARouterMap.LOGIN)
                        for (i in ActivityCacheManager.instance().getAllActivity()) {
                            if (i !is LoginActivity) {
                                i.finish()
                            }
                        }
                    }
                }
            }
            workAttendanceRecordLiveData.observe(this@WorkAttendanceActivity) {
                dismissProgressDialog()
                signState = it.signState
                when (it.signState) {
                    "01" -> {
                        binding.tvLogInOut.text = i18n(com.rt.base.R.string.上班打卡)
                        binding.tvOnWorkTime.text = ""
                        binding.tvOffWorkTime.text = ""
                        binding.rtvOnWorkStatus.text = i18n(com.rt.base.R.string.未签到)
                        binding.rtvOffWorkStatus.text = i18n(com.rt.base.R.string.未签退)
                        binding.rtvOnWorkRangeStatus.gone()
                    }

                    "02" -> {
                        binding.tvLogInOut.text = i18n(com.rt.base.R.string.下班打卡)
                        binding.tvOnWorkTime.text = it.onTime
                        binding.rtvOnWorkRangeStatus.show()
                        binding.rtvOffWorkStatus.show()
                        binding.rtvOffWorkRangeStatus.gone()
                        if (it.onState == "00") {
                            binding.rtvOnWorkStatus.text = i18n(com.rt.base.R.string.正常)
                        } else if (it.onState == "01") {
                            binding.rtvOnWorkStatus.text = i18n(com.rt.base.R.string.迟到)
                        }
                        if (it.locationState == "00") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
                        } else if (it.onState == "01") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
                        } else if (it.onState == "02") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
                        } else if (it.onState == "03") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
                        }
                        binding.rtvOffWorkStatus.text = i18n(com.rt.base.R.string.未签退)
                    }

                    "03" -> {
                        binding.tvLogInOut.text = i18n(com.rt.base.R.string.下班打卡)
                        binding.tvOnWorkTime.text = it.onTime
                        binding.tvOffWorkTime.text = it.offTime
                        binding.rtvOnWorkRangeStatus.show()
                        binding.rtvOffWorkStatus.show()
                        binding.rtvOffWorkRangeStatus.show()
                        if (it.onState == "00") {
                            binding.rtvOnWorkStatus.text = i18n(com.rt.base.R.string.正常)
                        } else if (it.onState == "01") {
                            binding.rtvOnWorkStatus.text = i18n(com.rt.base.R.string.迟到)
                        }
                        if (it.offState == "00") {
                            binding.rtvOffWorkStatus.text = i18n(com.rt.base.R.string.正常)
                        } else if (it.offState == "02") {
                            binding.rtvOffWorkStatus.text = i18n(com.rt.base.R.string.早退)
                        }
                        if (it.locationState == "00") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
                            binding.rtvOffWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
                        } else if (it.onState == "01") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
                            binding.rtvOffWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
                        } else if (it.onState == "02") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.正常范围)
                            binding.rtvOffWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
                        } else if (it.onState == "03") {
                            binding.rtvOnWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
                            binding.rtvOffWorkRangeStatus.text = i18n(com.rt.base.R.string.超出范围)
                        }
                    }
                }
            }
            errMsg.observe(this@WorkAttendanceActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@WorkAttendanceActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        GlobalScope.launch(Dispatchers.IO) {
            job?.cancelAndJoin()
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityWorkAttendanceBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<WorkAttendanceViewModel> {
        return WorkAttendanceViewModel::class.java
    }
}