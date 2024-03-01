package com.rt.inspector.ui.activity.violation

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.EnterpriseViolationHistoryBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.adapter.EnterpriseViolationHistoryAdapter
import com.rt.inspector.databinding.ActivityEnterpriseViolationHistoryBinding
import com.rt.inspector.mvvm.viewmodel.EnterpriseViolationHistoryViewModel
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.ENTERPRISE_VIOLATION_HISTORY)
class EnterpriseViolationHistoryActivity : VbBaseActivity<EnterpriseViolationHistoryViewModel, ActivityEnterpriseViolationHistoryBinding>(),
    OnClickListener {
    var enterpriseViolationHistoryAdapter: EnterpriseViolationHistoryAdapter? = null
    var enterpriseViolationHistoryList: MutableList<EnterpriseViolationHistoryBean> = ArrayList()
    var loginName = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.企业违规历史)

        binding.rvHistory.setHasFixedSize(true)
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        enterpriseViolationHistoryAdapter = EnterpriseViolationHistoryAdapter(enterpriseViolationHistoryList, this)
        binding.rvHistory.adapter = enterpriseViolationHistoryAdapter
        var noData = View.inflate(this, R.layout.layout_no_data, null)
        enterpriseViolationHistoryAdapter?.setEmptyView(noData)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            showProgressDialog(5000)
            val param = HashMap<String, Any>()
            val jsonobject = JSONObject()
            jsonobject["loginName"] = loginName
            param["attr"] = jsonobject
            mViewModel.queryEnterpriseViolationHistory(param)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.ll_history -> {
                val enterpriseViolationHistoryBean = v.tag as EnterpriseViolationHistoryBean
                startArouter(ARouterMap.ENTERPRISE_VIOLATION_DETAIL, data = Bundle().apply {
                    putString(ARouterMap.ENTERPRISE_MVIOLATEID, enterpriseViolationHistoryBean.mviolateId)
                })
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            queryEnterpriseViolationHistoryLiveData.observe(this@EnterpriseViolationHistoryActivity) {
                dismissProgressDialog()
                enterpriseViolationHistoryList = it.result as MutableList<EnterpriseViolationHistoryBean>
                enterpriseViolationHistoryAdapter?.setList(enterpriseViolationHistoryList)
            }
            errMsg.observe(this@EnterpriseViolationHistoryActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@EnterpriseViolationHistoryActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityEnterpriseViolationHistoryBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<EnterpriseViolationHistoryViewModel> {
        return EnterpriseViolationHistoryViewModel::class.java
    }

}