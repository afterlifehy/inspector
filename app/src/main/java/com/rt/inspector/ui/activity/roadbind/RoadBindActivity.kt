package com.rt.inspector.ui.activity.roadbind

import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.Road
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18n
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.adapter.RoadBindAdapter
import com.rt.inspector.databinding.ActivityRoadBindBinding
import com.rt.inspector.mvvm.viewmodel.RoadBindViewModel
import com.rt.inspector.pop.RoadSelectDialog
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.ROAD_BIND)
class RoadBindActivity : VbBaseActivity<RoadBindViewModel, ActivityRoadBindBinding>(), OnClickListener {
    var roadBindAdapter: RoadBindAdapter? = null
    var roadBindList: MutableList<Road> = ArrayList()
    var loginName = ""
    var roadSelectDialog: RoadSelectDialog? = null
    var roadUnbindList: MutableList<Road> = ArrayList()
    var chooseList: MutableList<Road> = ArrayList()

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.路段绑定)

        binding.rvStreet.setHasFixedSize(true)
        binding.rvStreet.layoutManager = LinearLayoutManager(this)
        roadBindAdapter = RoadBindAdapter(roadBindList)
        binding.rvStreet.adapter = roadBindAdapter
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvRoad.setOnClickListener(this)
        binding.rtvSearch.setOnClickListener(this)
        binding.rtvBind.setOnClickListener(this)
        binding.rtvUnbind.setOnClickListener(this)
    }

    override fun initData() {
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
        }
    }

    fun getBindRoadInfo() {
        val param = HashMap<String, Any>()
        val jsonobject = JSONObject()
        jsonobject["managerAccount"] = binding.etAccount.text.toString()
        jsonobject["loginName"] = loginName
        param["attr"] = jsonobject
        mViewModel.getBindRoadInfo(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_road -> {
                roadSelectDialog = RoadSelectDialog(roadUnbindList, chooseList, object : RoadSelectDialog.RoadChooseCallBack {
                    override fun chooseRoad() {
                        binding.tvRoad.text = chooseList.map { it.streetName }.joinToString(",")
                    }
                })
                roadSelectDialog?.show()
            }

            R.id.rtv_search -> {
                if (binding.etAccount.text.toString().isEmpty()) {
                    ToastUtil.showMiddleToast("请输入协管员账号")
                    return
                }
                getBindRoadInfo()
            }

            R.id.rtv_bind -> {
                if (binding.etAccount.text.toString().isEmpty()) {
                    ToastUtil.showMiddleToast("请输入协管员账号")
                    return
                }
                if (binding.etStreetNo.text.toString().isEmpty()) {
                    ToastUtil.showMiddleToast("请输入路段编号")
                    return
                }
                showProgressDialog(5000)
                val param = HashMap<String, Any>()
                val jsonobject = JSONObject()
                jsonobject["loginName"] = loginName
                jsonobject["managerAccount"] = binding.etAccount.text.toString()
                jsonobject["streetNo"] = binding.etStreetNo.text.toString()
                param["attr"] = jsonobject
                mViewModel.bindRoad(param)
            }

            R.id.rtv_unbind -> {
                if (binding.etAccount.text.toString().isEmpty()) {
                    ToastUtil.showMiddleToast("请输入协管员账号")
                    return
                }
                showProgressDialog(5000)
                val param = HashMap<String, Any>()
                val jsonobject = JSONObject()
                jsonobject["loginName"] = loginName
                jsonobject["managerAccount"] = binding.etAccount.text.toString()
                param["attr"] = jsonobject
                mViewModel.unbindRoad(param)
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            getBindRoadInfoLiveData.observe(this@RoadBindActivity) {
                dismissProgressDialog()
                if (it != null && it.result != null) {
                    roadBindList = it.result as MutableList<Road>
                    roadBindAdapter?.setList(roadBindList)
                } else {
                    ToastUtil.showMiddleToast("无绑定路段")
                    roadBindList.clear()
                    roadBindAdapter?.setList(roadBindList)
                }
            }
            bindRoadLiveData.observe(this@RoadBindActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast("绑定成功")
                getBindRoadInfo()
//                getUnBindRoadList()
            }
            unbindRoadLiveData.observe(this@RoadBindActivity) {
                ToastUtil.showMiddleToast("解绑成功")
                dismissProgressDialog()
                roadBindList.clear()
                roadBindAdapter?.setList(roadBindList)
            }
            errMsg.observe(this@RoadBindActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@RoadBindActivity) {
                ToastUtil.showMiddleToast(it.message)
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityRoadBindBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<RoadBindViewModel> {
        return RoadBindViewModel::class.java
    }
}