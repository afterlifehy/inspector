package com.rt.inspector.ui.activity.parking

import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityParkingOrderDetailBinding
import com.rt.inspector.mvvm.viewmodel.ParkingOrderDetailViewModel

@Route(path = ARouterMap.PARKING_ORDER_DETAIL)
class ParkingOrderDetailActivity : VbBaseActivity<ParkingOrderDetailViewModel, ActivityParkingOrderDetailBinding>(), OnClickListener {
    var orderNo = ""
    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.泊位订单详细信息)

        orderNo = intent.getStringExtra(ARouterMap.PARKING_ORDER_NO).toString()
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        showProgressDialog(20000)
        val param = HashMap<String, Any>()
        val jsonobject = JSONObject()
        jsonobject["orderNo"] = orderNo
        param["attr"] = jsonobject
        mViewModel.parkingLotDetail(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            parkingLotDetailLiveData.observe(this@ParkingOrderDetailActivity) {
                dismissProgressDialog()
                binding.tvParkingNo.text = it.parkingNo
                binding.tvPlateNo.text = it.carLicense
                binding.tvEnterTime.text = it.startTime
                binding.tvInputTime.text = it.inputTime
                binding.tvParkingTime.text = it.duration
                binding.tvPaidAmount.text = it.paidAmount
                binding.tvRealAmount.text = it.amount
            }
            errMsg.observe(this@ParkingOrderDetailActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@ParkingOrderDetailActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityParkingOrderDetailBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<ParkingOrderDetailViewModel> {
        return ParkingOrderDetailViewModel::class.java
    }

}