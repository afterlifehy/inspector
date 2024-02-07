package com.rt.inspector.ui.activity.roadbind

import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.adapter.RoadBindAdapter
import com.rt.inspector.databinding.ActivityRoadBindBinding
import com.rt.inspector.mvvm.viewmodel.RoadBindViewModel

@Route(path = ARouterMap.ROAD_BIND)
class RoadBindActivity : VbBaseActivity<RoadBindViewModel, ActivityRoadBindBinding>(), OnClickListener {
    var roadBindAdapter: RoadBindAdapter? = null
    var roadBindList: MutableList<Int> = ArrayList()

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.路段绑定)

        binding.rvStreet.setHasFixedSize(true)
        binding.rvStreet.layoutManager = LinearLayoutManager(this)
        roadBindAdapter = RoadBindAdapter(roadBindList)
        binding.rvStreet.adapter = roadBindAdapter
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.rtvSearch.setOnClickListener(this)
        binding.rtvBind.setOnClickListener(this)
    }

    override fun initData() {
        roadBindList.add(1)
        roadBindList.add(2)
        roadBindList.add(3)
        roadBindList.add(4)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.rtv_search -> {
                if (binding.etAccount.text.toString().isEmpty()) {
                    ToastUtil.showMiddleToast("请输入协管员账号")
                    return
                }
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