package com.rt.inspector.ui.activity.infoverify

import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityInfoVerifyMainBinding
import com.rt.inspector.mvvm.viewmodel.InfoVerifyViewModel

@Route(path = ARouterMap.INFO_VERIFY_MAIN)
class InfoVerifyMainActivity : VbBaseActivity<InfoVerifyViewModel, ActivityInfoVerifyMainBinding>(), OnClickListener {

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.信息核查)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvBusinessLicense.setOnClickListener(this)
        binding.tvFeeStandards.setOnClickListener(this)
        binding.tvParkingInfo.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_businessLicense -> {
                startArouter(ARouterMap.BUSINESS_LICENSE)
            }

            R.id.tv_feeStandards -> {
                startArouter(ARouterMap.FEE_STANDARD)
            }

            R.id.tv_parkingInfo -> {
                startArouter(ARouterMap.ENTERPRISE_VIOLATION_REPORT)
            }

        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityInfoVerifyMainBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<InfoVerifyViewModel> {
        return InfoVerifyViewModel::class.java
    }
}