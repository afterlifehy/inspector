package com.rt.inspector.ui.activity.parking

import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.ParkingLotBean
import com.rt.base.ext.show
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.Constant
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.adapter.ParkingLotAdapter
import com.rt.inspector.databinding.ActivityParkingLotBinding
import com.rt.inspector.mvvm.viewmodel.ParkingLotViewModel

@Route(path = ARouterMap.PARKING_LOT)
class ParkingLotActivity : VbBaseActivity<ParkingLotViewModel, ActivityParkingLotBinding>(), OnClickListener {
    var parkingLotAdapter: ParkingLotAdapter? = null
    var parkingLotList: MutableList<ParkingLotBean> = ArrayList()
    var count = 0
    var handler = Handler(Looper.getMainLooper())

    override fun initView() {
        GlideUtils.instance?.loadImage(binding.layoutToolbar.ivBack, com.rt.common.R.mipmap.ic_back)
        GlideUtils.instance?.loadImage(binding.layoutToolbar.ivRight, com.rt.common.R.mipmap.ic_traffic_assistant_logo)
        binding.layoutToolbar.ivRight.show()

        binding.rvParkingLot.setHasFixedSize(true)
        binding.rvParkingLot.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        parkingLotAdapter = ParkingLotAdapter(parkingLotList, this)
        binding.rvParkingLot.adapter = parkingLotAdapter
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.layoutToolbar.ivRight.setOnClickListener(this)
    }

    override fun initData() {

    }

    val runnable = object : Runnable {
        override fun run() {
            if (count < 600) {
                getParkingLotList()
                count++
                handler.postDelayed(this, 10000)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.post(runnable)
    }

    fun getParkingLotList() {
        parkingLotList.clear()
        parkingLotList.add(ParkingLotBean(Constant.BLUE, "沪DWU073", "", "", "CN-009-001", "01", 1706499027000))
        parkingLotList.add(ParkingLotBean(Constant.BLUE, "沪DWU073", "", "", "CN-009-002", "02", 1738121427000))
        parkingLotList.add(ParkingLotBean(Constant.YELLOW_GREEN, "沪DWU073", "", "", "CN-009-003", "02", 1738121427000))
        parkingLotList.add(ParkingLotBean(Constant.BLUE, "沪DWU073", "", "", "CN-009-004", "02", 1738121427000))
        parkingLotList.add(ParkingLotBean(Constant.YELLOW, "沪DWU073", "", "", "CN-009-005", "02", 1706499027000))
        parkingLotList.add(ParkingLotBean(Constant.GREEN, "沪DWU073", "", "", "CN-009-006", "02", 1706499027000))
        parkingLotList.add(ParkingLotBean(Constant.BLUE, "沪DWU073", "", "", "CN-009-007", "02", 1706499027000))
        parkingLotAdapter?.setList(parkingLotList)
//        showProgressDialog(20000)
//        val param = HashMap<String, Any>()
//        val jsonobject = JSONObject()
//        jsonobject["streetNo"] = currentStreet!!.streetNo
//        param["attr"] = jsonobject
//        mViewModel.getParkingLotList(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.rfl_parking -> {
//                val parkingLotBean = v.tag as ParkingLotBean
//                if (parkingLotBean.state == "01") {
//                    startArouter(ARouterMap.ADMISSION_TAKE_PHOTO, data = Bundle().apply {
//                        putString(ARouterMap.ADMISSION_TAKE_PHOTO_PARKING_NO, parkingLotBean.parkingNo)
//                        putInt(ARouterMap.ADMISSION_TAKE_PHOTO_PARKING_AMOUNT, parkingLotList.size)
//                    })
//                } else {
//                    startArouter(ARouterMap.PARKING_SPACE, data = Bundle().apply {
//                        putString(ARouterMap.ORDER_NO, parkingLotBean.orderNo)
//                        putString(ARouterMap.CAR_LICENSE, parkingLotBean.carLicense)
//                    })
//                }
            }

            R.id.iv_right -> {
                startArouter(ARouterMap.TRAFFIC_ASSISTANT_LIST)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
//            parkingLotListLiveData.observe(this@ParkingLotActivity) {
//                dismissProgressDialog()
//                parkingLotList.clear()
//                parkingLotList.addAll(it.result)
//                parkingLotAdapter?.setList(parkingLotList)
//            }
            errMsg.observe(this@ParkingLotActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@ParkingLotActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityParkingLotBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<ParkingLotViewModel> {
        return ParkingLotViewModel::class.java
    }

    override fun onDestroy() {
        super.onDestroy()
        if (handler != null) {
            handler.removeCallbacks(runnable)
        }
    }
}