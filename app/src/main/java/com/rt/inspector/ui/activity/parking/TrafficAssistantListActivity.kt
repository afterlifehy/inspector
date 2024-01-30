package com.rt.inspector.ui.activity.parking

import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.adapter.TrafficAssistantAdapter
import com.rt.inspector.databinding.ActivityTrafficAssistantListBinding
import com.rt.inspector.mvvm.viewmodel.TrafficAssistantListViewModel

@Route(path = ARouterMap.TRAFFIC_ASSISTANT_LIST)
class TrafficAssistantListActivity : VbBaseActivity<TrafficAssistantListViewModel, ActivityTrafficAssistantListBinding>(), OnClickListener {
    var trafficAssistantAdapter: TrafficAssistantAdapter? = null
    var trafficAssistantList: MutableList<Int> = ArrayList()

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.当班协管员)
        binding.rvTrafficAssistant.setHasFixedSize(true)
        binding.rvTrafficAssistant.layoutManager = LinearLayoutManager(this)
        trafficAssistantAdapter = TrafficAssistantAdapter(trafficAssistantList, this)
        binding.rvTrafficAssistant.adapter = trafficAssistantAdapter
        trafficAssistantAdapter?.setEmptyView(R.layout.layout_no_data)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        trafficAssistantList.add(1)
        trafficAssistantList.add(2)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.ll_trafficAssistant -> {
                startArouter(ARouterMap.INCOME_COUNTING)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            errMsg.observe(this@TrafficAssistantListActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@TrafficAssistantListActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.message)
            }
        }
    }

    override fun providerVMClass(): Class<TrafficAssistantListViewModel>? {
        return TrafficAssistantListViewModel::class.java
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityTrafficAssistantListBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View? {
        return binding.layoutToolbar.toolbar
    }

}