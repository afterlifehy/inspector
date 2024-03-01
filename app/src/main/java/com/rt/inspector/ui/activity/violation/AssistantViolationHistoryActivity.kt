package com.rt.inspector.ui.activity.violation

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.AssistantViolationHistoryBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18n
import com.rt.base.ext.show
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.adapter.AssistantViolationHistoryAdapter
import com.rt.inspector.databinding.ActivityAssistantViolationHistoryBinding
import com.rt.inspector.dialog.SearchDialog
import com.rt.inspector.mvvm.viewmodel.AssistantViolationHistoryViewModel
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.ASSISTANT_VIOLATION_HISTORY)
class AssistantViolationHistoryActivity : VbBaseActivity<AssistantViolationHistoryViewModel, ActivityAssistantViolationHistoryBinding>(),
    OnClickListener {
    var assistantViolationHistoryAdapter: AssistantViolationHistoryAdapter? = null
    var assistantViolationHistoryList: MutableList<AssistantViolationHistoryBean> = ArrayList()
    var searchDialog: SearchDialog? = null
    var loginName = ""

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
        binding.layoutToolbar.ivRight.setOnClickListener(this)
    }

    override fun initData() {
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            queryAssistantViolationHistory("")
        }
    }

    fun queryAssistantViolationHistory(query: String) {
        showProgressDialog(5000)
        val param = HashMap<String, Any>()
        val jsonobject = JSONObject()
        jsonobject["loginName"] = loginName
        jsonobject["query"] = query
        param["attr"] = jsonobject
        mViewModel.queryAssistantViolationHistory(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.ll_history -> {
                val assistantViolationHistoryBean = v.tag as AssistantViolationHistoryBean
                startArouter(ARouterMap.ASSISTANT_VIOLATION_DETAIL,data = Bundle().apply {
                    putString(ARouterMap.ASSISTANT_MVIOLATEID,assistantViolationHistoryBean.mviolateId)
                })
            }

            R.id.iv_right -> {
                searchDialog = SearchDialog("输入场库编号/协管员账号", object : SearchDialog.SearchCallBack {
                    override fun confirm(queryTxt: String) {
                        queryAssistantViolationHistory(queryTxt)
                    }

                })
                searchDialog?.show()
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            queryAssistantViolationHistoryLiveData.observe(this@AssistantViolationHistoryActivity) {
                dismissProgressDialog()
                assistantViolationHistoryList = it.result as MutableList<AssistantViolationHistoryBean>
                assistantViolationHistoryAdapter?.setList(assistantViolationHistoryList)
            }
            errMsg.observe(this@AssistantViolationHistoryActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@AssistantViolationHistoryActivity) {
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