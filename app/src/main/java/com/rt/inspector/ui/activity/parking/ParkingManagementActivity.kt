package com.rt.inspector.ui.activity.parking

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.TimeUtils
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.ParkingManagementBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.gone
import com.rt.base.ext.i18n
import com.rt.base.ext.show
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.view.datapicker.CustomDatePicker
import com.rt.inspector.R
import com.rt.inspector.adapter.ParkingManagementAdapter
import com.rt.inspector.databinding.ActivityParkingManagementBinding
import com.rt.inspector.mvvm.viewmodel.ParkingManagementViewModel
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.PARKING_MANAGEMENT)
class ParkingManagementActivity : VbBaseActivity<ParkingManagementViewModel, ActivityParkingManagementBinding>(), OnClickListener {
    var parkingList: MutableList<ParkingManagementBean> = ArrayList()
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
        var noData = View.inflate(this, R.layout.layout_no_data, null)
        noData.findViewById<TextView>(R.id.tv_noDataContent).text = ""
        parkingManagementAdapter?.setEmptyView(noData)

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
                binding.srlParking.finishRefresh(5000)
                parkingList.clear()
                query()
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
        showProgressDialog(5000)
        runBlocking {
            val param = HashMap<String, Any>()
            val jsonobject = JSONObject()
            jsonobject["queryDate"] = selectTime
            jsonobject["loginName"] = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            jsonobject["page"] = pageIndex
            jsonobject["pageSize"] = pageSize
            param["attr"] = jsonobject
            mViewModel.parkingManagementList(param)
        }
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
                    customDatePicker?.show(System.currentTimeMillis())
                } else {
                    customDatePicker?.show(selectTime)
                }
            }

            R.id.rll_parking -> {
                val parkingManagementBean = v.tag as ParkingManagementBean
                startArouter(ARouterMap.PARKING_LOT, data = Bundle().apply {
                    putString(ARouterMap.PARKING_LOT_STREET_NAME, parkingManagementBean.streetName)
                    putString(ARouterMap.PARKING_LOT_STREET_NO, parkingManagementBean.streetNo)
                })
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            parkingManagementListLiveData.observe(this@ParkingManagementActivity) {
                dismissProgressDialog()
                val tempList = it.result
                if (pageIndex == 1) {
                    parkingList.clear()
                    parkingList.addAll(tempList)
                    parkingManagementAdapter?.setList(parkingList)
                    binding.srlParking.finishRefresh()
                } else {
                    if (tempList.isEmpty()) {
                        pageIndex--
                        binding.srlParking.finishLoadMoreWithNoMoreData()
                    } else {
                        parkingList.addAll(tempList)
                        parkingManagementAdapter?.setList(parkingList)
                        binding.srlParking.finishLoadMore(300)
                    }
                }
            }
            errMsg.observe(this@ParkingManagementActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@ParkingManagementActivity) {
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