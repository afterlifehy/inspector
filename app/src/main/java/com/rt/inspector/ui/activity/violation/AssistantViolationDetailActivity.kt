package com.rt.inspector.ui.activity.violation

import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.AppUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityAssistantViolationDetailBinding
import com.rt.inspector.mvvm.viewmodel.AssistantViolationDetailViewModel

@Route(path = ARouterMap.ASSISTANT_VIOLATION_DETAIL)
class AssistantViolationDetailActivity : VbBaseActivity<AssistantViolationDetailViewModel, ActivityAssistantViolationDetailBinding>(),
    OnClickListener {

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.协管员违规详情)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityAssistantViolationDetailBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<AssistantViolationDetailViewModel>? {
        return AssistantViolationDetailViewModel::class.java
    }
}