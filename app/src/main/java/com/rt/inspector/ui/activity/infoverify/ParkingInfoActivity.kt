package com.rt.inspector.ui.activity.infoverify

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
import com.rt.base.bean.QueryParkingInfoBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18n
import com.rt.base.ext.show
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.adapter.ParkingInfoAdapter
import com.rt.inspector.databinding.ActivityParkingInfoBinding
import com.rt.inspector.dialog.SearchDialog
import com.rt.inspector.mvvm.viewmodel.ParkingInfoViewModel
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.PARKING_INFO)
class ParkingInfoActivity : VbBaseActivity<ParkingInfoViewModel, ActivityParkingInfoBinding>(),
    OnClickListener {
    var parkingInfoAdapter: ParkingInfoAdapter? = null
    var parkingngInfoList: MutableList<QueryParkingInfoBean> = ArrayList()
    var searchDialog: SearchDialog? = null
    var loginName = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.停车人信息)
        binding.layoutToolbar.ivRight.show()
        GlideUtils.instance?.loadImage(binding.layoutToolbar.ivRight, com.rt.common.R.mipmap.ic_search)

        binding.rvParkingInfo.setHasFixedSize(true)
        binding.rvParkingInfo.layoutManager = LinearLayoutManager(this)
        parkingInfoAdapter = ParkingInfoAdapter(parkingngInfoList, this)
        binding.rvParkingInfo.adapter = parkingInfoAdapter
        var noData = View.inflate(this, R.layout.layout_no_data, null)
        GlideUtils.instance?.loadImage(noData.findViewById<ImageView>(R.id.iv_noData), com.rt.common.R.mipmap.ic_no_search_result)
        noData.findViewById<TextView>(R.id.tv_noDataTitle).text = i18n(com.rt.base.R.string.无搜索结果)
        noData.findViewById<TextView>(R.id.tv_noDataContent).text = i18n(com.rt.base.R.string.切换关键词重新搜索试试看)
        parkingInfoAdapter?.setEmptyView(noData)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.layoutToolbar.ivRight.setOnClickListener(this)
    }

    override fun initData() {
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.name)
            queryParkingInfo("")
        }
    }

    fun queryParkingInfo(queryTxt: String) {
        val param = HashMap<String, Any>()
        val jsonobject = JSONObject()
        jsonobject["phone"] = queryTxt
        jsonobject["loginName"] = loginName
        param["attr"] = jsonobject
        mViewModel.queryParkingInfo(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.ll_history -> {
            }

            R.id.iv_right -> {
                searchDialog = SearchDialog(i18n(com.rt.base.R.string.输入停车人手机号), object : SearchDialog.SearchCallBack {
                    override fun confirm(queryTxt: String) {
                        queryParkingInfo(queryTxt)
                    }

                })
                searchDialog?.show()
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            queryParkingInfoLiveData.observe(this@ParkingInfoActivity) {
                parkingngInfoList = it.result as MutableList<QueryParkingInfoBean>
                parkingInfoAdapter?.setList(parkingngInfoList)
            }
            errMsg.observe(this@ParkingInfoActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@ParkingInfoActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityParkingInfoBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<ParkingInfoViewModel> {
        return ParkingInfoViewModel::class.java
    }

}