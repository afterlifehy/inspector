package com.rt.inspector.ui.activity.infoverify

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
import com.rt.inspector.adapter.ParkingInfoAdapter
import com.rt.inspector.databinding.ActivityParkingInfoBinding
import com.rt.inspector.dialog.SearchDialog
import com.rt.inspector.mvvm.viewmodel.ParkingInfoViewModel

@Route(path = ARouterMap.PARKING_INFO)
class ParkingInfoActivity : VbBaseActivity<ParkingInfoViewModel, ActivityParkingInfoBinding>(),
    OnClickListener {
    var parkingInfoAdapter: ParkingInfoAdapter? = null
    var parkigngInfoList: MutableList<Int> = ArrayList()
    var searchDialog: SearchDialog? = null

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.停车人信息)
        binding.layoutToolbar.ivRight.show()
        GlideUtils.instance?.loadImage(binding.layoutToolbar.ivRight, com.rt.common.R.mipmap.ic_search)

        binding.rvParkingInfo.setHasFixedSize(true)
        binding.rvParkingInfo.layoutManager = LinearLayoutManager(this)
        parkingInfoAdapter = ParkingInfoAdapter(parkigngInfoList, this)
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
        parkigngInfoList.add(1)
        parkigngInfoList.add(2)
        parkigngInfoList.add(3)
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
                    override fun confirm(street: String) {

                    }

                })
                searchDialog?.show()
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
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