package com.rt.inspector.ui.activity.violation

import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.ext.show
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.adapter.AssistantViolationHistoryAdapter
import com.rt.inspector.databinding.ActivityAssistantViolationHistoryBinding
import com.rt.inspector.mvvm.viewmodel.AssistantViolationHistoryViewModel

@Route(path = ARouterMap.ASSISTANT_VIOLATION_HISTORY)
class AssistantViolationHistoryActivity : VbBaseActivity<AssistantViolationHistoryViewModel, ActivityAssistantViolationHistoryBinding>(),
    OnClickListener {
    var assistantViolationHistoryAdapter: AssistantViolationHistoryAdapter? = null
    var assistantViolationHistoryList: MutableList<Int> = ArrayList()
    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.协管员违规历史)
        binding.layoutToolbar.ivRight.show()
        GlideUtils.instance?.loadImage(binding.layoutToolbar.ivRight, com.rt.common.R.mipmap.ic_search)

        binding.rvHistory.setHasFixedSize(true)
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        assistantViolationHistoryAdapter = AssistantViolationHistoryAdapter(assistantViolationHistoryList, this)
        binding.rvHistory.adapter = assistantViolationHistoryAdapter
        var noData = View.inflate(this, R.layout.layout_no_data, null)
        GlideUtils.instance?.loadImage(noData.findViewById<ImageView>(R.id.iv_noData), com.rt.common.R.mipmap.ic_no_search_result)
        noData.findViewById<TextView>(R.id.tv_noDataTitle).text = i18n(com.rt.base.R.string.无搜索结果)
        noData.findViewById<TextView>(R.id.tv_noDataContent).text = i18n(com.rt.base.R.string.切换关键词重新搜索试试看)
        assistantViolationHistoryAdapter?.setEmptyView(noData)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        assistantViolationHistoryList.add(1)
        assistantViolationHistoryList.add(2)
        assistantViolationHistoryList.add(3)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.ll_history -> {
                startArouter(ARouterMap.ASSISTANT_VIOLATION_DETAIL)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            errMsg.observe(this@AssistantViolationHistoryActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@AssistantViolationHistoryActivity){
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityAssistantViolationHistoryBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<AssistantViolationHistoryViewModel> {
        return AssistantViolationHistoryViewModel::class.java
    }

}