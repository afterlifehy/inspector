package com.rt.inspector.ui.activity.violation

import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityViolationReportMainBinding
import com.rt.inspector.mvvm.viewmodel.ViolationReportMainViewModel

@Route(path = ARouterMap.VIOLATION_REPORT_MAIN)
class ViolationReportMainActivity : VbBaseActivity<ViolationReportMainViewModel, ActivityViolationReportMainBinding>(), OnClickListener {

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.违规上报)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvAssistantViolationReport.setOnClickListener(this)
        binding.tvAssistantViolationHistory.setOnClickListener(this)
        binding.tvEnterpriseViolationReport.setOnClickListener(this)
        binding.tvEnterpriseViolationHistory.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_assistantViolationReport -> {
                startArouter(ARouterMap.ASSISTANT_VIOLATION_REPORT)
            }

            R.id.tv_assistantViolationHistory -> {
                startArouter(ARouterMap.ASSISTANT_VIOLATION_HISTORY)
            }

            R.id.tv_enterpriseViolationReport -> {

            }

            R.id.tv_enterpriseViolationHistory -> {

            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityViolationReportMainBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<ViolationReportMainViewModel>? {
        return ViolationReportMainViewModel::class.java
    }
}