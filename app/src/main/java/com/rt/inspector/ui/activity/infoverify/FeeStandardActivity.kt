package com.rt.inspector.ui.activity.infoverify

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
import com.rt.inspector.adapter.FeeStandardAdapter
import com.rt.inspector.databinding.ActivityFeeStandardBinding
import com.rt.inspector.mvvm.viewmodel.FeeStandardViewModel

@Route(path = ARouterMap.FEE_STANDARD)
class FeeStandardActivity : VbBaseActivity<FeeStandardViewModel, ActivityFeeStandardBinding>(), OnClickListener {
    var feeStandardAdapter: FeeStandardAdapter? = null
    var streetList: MutableList<Int> = ArrayList()
    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.收费标准)

        binding.rvFeeStandards.setHasFixedSize(true)
        binding.rvFeeStandards.layoutManager = LinearLayoutManager(this)
        feeStandardAdapter = FeeStandardAdapter(streetList)
        binding.rvFeeStandards.adapter = feeStandardAdapter
        feeStandardAdapter?.setExpandCallback(object : FeeStandardAdapter.ExpandFeeStandardCallback {
            override fun expand(street: Int) {

            }
        })
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        streetList.add(1)
        streetList.add(2)
        streetList.add(3)
        streetList.add(4)
        streetList.add(5)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityFeeStandardBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<FeeStandardViewModel>? {
        return FeeStandardViewModel::class.java
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            errMsg.observe(this@FeeStandardActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@FeeStandardActivity) {
                dismissProgressDialog()
            }
        }
    }
}