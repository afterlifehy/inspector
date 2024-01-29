package com.rt.inspector.ui.activity.parking

import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.TimeUtils
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.view.datapicker.CustomDatePicker
import com.rt.inspector.R
import com.rt.inspector.adapter.ParkingManagementAdapter
import com.rt.inspector.databinding.ActivityParkingManagementBinding
import com.rt.inspector.mvvm.viewmodel.ParkingManagementViewModel

@Route(path = ARouterMap.PARKING_MANAGEMENT)
class ParkingManagementActivity : VbBaseActivity<ParkingManagementViewModel, ActivityParkingManagementBinding>(), OnClickListener {
    var parkingList: MutableList<Int> = ArrayList()
    var parkingManagementAdapter: ParkingManagementAdapter? = null
    var pageIndex = 1
    var pageSize = 10
    var customDatePicker: CustomDatePicker? = null
    var selectTime = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.场库管理)

        binding.rvParking.setHasFixedSize(true)
        binding.rvParking.layoutManager = LinearLayoutManager(this)
        parkingManagementAdapter = ParkingManagementAdapter(parkingList, this)
        binding.rvParking.adapter = parkingManagementAdapter

        selectTime = TimeUtils.millis2String(System.currentTimeMillis(), "yyyy-MM-dd")
        binding.tvDate.text = selectTime

        initDatePicker()
    }

    private fun initDatePicker() {
        val beginTimestamp = TimeUtils.string2Millis("2020-01-01", "yyyy-MM-dd")
        val endTimestamp = System.currentTimeMillis()

        // 通过时间戳初始化日期，毫秒级别
        customDatePicker = CustomDatePicker(this, object : CustomDatePicker.Callback {
            override fun reset() {
            }

            override fun onTimeSelected(timestamp: Long) {
                selectTime = TimeUtils.millis2String(timestamp, "yyyy-MM-dd")
                binding.tvDate.text = selectTime
                pageIndex = 1
            }
        }, beginTimestamp, endTimestamp)
        customDatePicker?.setResetVisibility(false)
        // 不允许点击屏幕或物理返回键关闭
        customDatePicker?.setCancelable(false)
        // 显示年月
        customDatePicker?.showTimeFormat(2)
        // 不允许循环滚动
        customDatePicker?.setScrollLoop(false)
        // 不允许滚动动画
        customDatePicker?.setCanShowAnim(false)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.ivDate.setOnClickListener(this)
        binding.srlParking.setOnRefreshListener {
            pageIndex = 1
            binding.srlParking.finishRefresh(5000)
            parkingList.clear()
            query()
        }
        binding.srlParking.setOnLoadMoreListener {
            pageIndex++
            binding.srlParking.finishLoadMore(5000)
            query()
        }
    }

    fun query() {
        parkingList.add(1)
        parkingList.add(2)
        parkingList.add(3)
        parkingList.add(4)
        parkingList.add(5)
        parkingList.add(6)
        parkingList.add(7)
        parkingList.add(8)
        parkingList.add(9)
        parkingManagementAdapter?.setList(parkingList)
    }

    override fun initData() {
        query()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.iv_date -> {
                if (selectTime.isEmpty()) {
                    customDatePicker?.show(selectTime)
                } else {
                    customDatePicker?.show(System.currentTimeMillis())
                }
            }

            R.id.rll_parking -> {
                startArouter(ARouterMap.PARKING_LOT)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            errMsg.observe(this@ParkingManagementActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@ParkingManagementActivity){
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.message)
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityParkingManagementBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<ParkingManagementViewModel> {
        return ParkingManagementViewModel::class.java
    }

}