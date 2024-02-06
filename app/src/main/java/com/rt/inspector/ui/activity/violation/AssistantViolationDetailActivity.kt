package com.rt.inspector.ui.activity.violation

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.AppUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityAssistantViolationDetailBinding
import com.rt.inspector.mvvm.viewmodel.AssistantViolationDetailViewModel

@Route(path = ARouterMap.ASSISTANT_VIOLATION_DETAIL)
class AssistantViolationDetailActivity : VbBaseActivity<AssistantViolationDetailViewModel, ActivityAssistantViolationDetailBinding>(),
    OnClickListener {
    var picList: MutableList<String> = ArrayList()

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.协管员违规详情)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.rivImg1.setOnClickListener(this)
        binding.rivImg2.setOnClickListener(this)
        binding.rivImg3.setOnClickListener(this)
    }

    override fun initData() {
        picList.add("")
        picList.add("")
        picList.add("")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.riv_img1 -> {
                startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                    putInt(ARouterMap.IMG_INDEX, 0)
                    putStringArrayList(ARouterMap.IMG_LIST, picList as ArrayList<String>)
                })
            }

            R.id.riv_img2 -> {
                startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                    putInt(ARouterMap.IMG_INDEX, 1)
                    putStringArrayList(ARouterMap.IMG_LIST, picList as ArrayList<String>)
                })
            }

            R.id.riv_img3 -> {
                startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                    putInt(ARouterMap.IMG_INDEX, 2)
                    putStringArrayList(ARouterMap.IMG_LIST, picList as ArrayList<String>)
                })
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            errMsg.observe(this@AssistantViolationDetailActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@AssistantViolationDetailActivity) {
                dismissProgressDialog()
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