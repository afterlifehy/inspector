package com.rt.inspector.ui.activity

import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18N
import com.rt.base.ext.startArouter
import com.rt.base.help.ActivityCacheManager
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.AppUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityMainBinding
import com.rt.inspector.mvvm.viewmodel.MainViewModel

@Route(path = ARouterMap.MAIN)
class MainActivity : VbBaseActivity<MainViewModel, ActivityMainBinding>(), OnClickListener {

    override fun initView() {

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

            }

            R.id.fl_taskReception -> {
                startArouter(ARouterMap.TASK_RECEPTION)
            }

            R.id.fl_info_verification -> {
                startArouter(ARouterMap.INFO_VERIFY_MAIN)
            }
        }
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