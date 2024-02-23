package com.rt.inspector.ui.activity.infoverify

import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.BusinessLicenseBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.adapter.BusinessLicenseAdapter
import com.rt.inspector.databinding.ActivityBusinessLicenseBinding
import com.rt.inspector.mvvm.viewmodel.BusinessLicenseViewModel
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.BUSINESS_LICENSE)
class BusinessLicenseActivity : VbBaseActivity<BusinessLicenseViewModel, ActivityBusinessLicenseBinding>(),
    OnClickListener {
    var businessLicenseAdapter: BusinessLicenseAdapter? = null
    var businessLicenseList: MutableList<BusinessLicenseBean> = ArrayList()

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.经营许可)

        binding.rvBusinessLicense.setHasFixedSize(true)
        binding.rvBusinessLicense.layoutManager = LinearLayoutManager(this)
        businessLicenseAdapter = BusinessLicenseAdapter(businessLicenseList, this)
        binding.rvBusinessLicense.adapter = businessLicenseAdapter
        var noData = View.inflate(this, R.layout.layout_no_data, null)
        GlideUtils.instance?.loadImage(noData.findViewById<ImageView>(R.id.iv_noData), com.rt.common.R.mipmap.ic_no_search_result)
        businessLicenseAdapter?.setEmptyView(noData)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        runBlocking {
            val loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            val param = HashMap<String, Any>()
            val jsonobject = JSONObject()
            jsonobject["loginName"] = loginName
            param["attr"] = jsonobject
            mViewModel.businessLicenseList(param)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.ll_businessLicense -> {
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            businessLicenseListLiveData.observe(this@BusinessLicenseActivity) {
                businessLicenseList.clear()
                businessLicenseList.addAll(it.result)
                businessLicenseAdapter?.setList(businessLicenseList)
            }
            errMsg.observe(this@BusinessLicenseActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@BusinessLicenseActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityBusinessLicenseBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<BusinessLicenseViewModel> {
        return BusinessLicenseViewModel::class.java
    }

}