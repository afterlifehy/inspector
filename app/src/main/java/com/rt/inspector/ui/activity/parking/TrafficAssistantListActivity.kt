package com.rt.inspector.ui.activity.parking

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.TrafficAssistantBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
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
    var trafficAssistantList: MutableList<TrafficAssistantBean> = ArrayList()
    var streetNo = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.当班协管员)

        streetNo = intent.getStringExtra(ARouterMap.TRAFFIC_ASSISTANT_STREET_NO).toString()

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
        showProgressDialog(5000)
        val param = HashMap<String, Any>()
        val jsonobject = JSONObject()
        jsonobject["streetNo"] = streetNo
        param["attr"] = jsonobject
        mViewModel.trafficAssistantList(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.ll_trafficAssistant -> {
                val trafficAssistantBean = v.tag as TrafficAssistantBean
                startArouter(ARouterMap.INCOME_COUNTING,data = Bundle().apply {
                    putString(ARouterMap.INCOME_COUNTING_STREET_NO,streetNo)
                    putString(ARouterMap.INCOME_MANAGE_COUNT,trafficAssistantBean.managerAccount)
                })
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            trafficAssistantListLiveData.observe(this@TrafficAssistantListActivity) {
                dismissProgressDialog()
                trafficAssistantList.clear()
                trafficAssistantList.addAll(it.result)
                trafficAssistantAdapter?.setList(trafficAssistantList)
            }
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

    override fun providerVMClass(): Class<TrafficAssistantListViewModel> {
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