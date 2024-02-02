package com.rt.inspector.ui.activity.parking

import android.content.Intent
import android.provider.Settings
import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.dialog.DialogHelp
import com.rt.base.ext.i18N
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.AppUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityIncomeCountingBinding
import com.rt.inspector.mvvm.viewmodel.IncomeCountingViewModel
import com.zrq.spanbuilder.TextStyle

@Route(path = ARouterMap.INCOME_COUNTING)
class IncomeCountingActivity : VbBaseActivity<IncomeCountingViewModel, ActivityIncomeCountingBinding>(), OnClickListener {
    val sizes = intArrayOf(33, 13)
    val colors = intArrayOf(com.rt.base.R.color.color_fff15a24, com.rt.base.R.color.color_ff1a1a1a)
    val sizes2 = intArrayOf(17, 17)
    val styles = arrayOf(TextStyle.BOLD, TextStyle.NORMAL)

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18N(com.rt.base.R.string.营收盘点)
        val strings = arrayOf("0", "笔")
        binding.tvOrderCount.text = AppUtil.getSpan(strings, sizes, colors,styles)
        val strings2 = arrayOf("0.00", "元")
        binding.tvIncome.text = AppUtil.getSpan(strings2, sizes, colors,styles)
        val strings3 = arrayOf("0.00", "元")
        binding.tvRecovered.text = AppUtil.getSpan(strings3, sizes2, colors, styles)
        val strings4 = arrayOf("0.00", "元")
        binding.tvRecoveryOthers.text = AppUtil.getSpan(strings4, sizes2, colors, styles)
        val strings5 = arrayOf("0.00", "元")
        binding.tvSelfRecovery.text = AppUtil.getSpan(strings5, sizes2, colors, styles)
        val strings6 = arrayOf("0.00", "元")
        binding.tvReceiveAmount.text = AppUtil.getSpan(strings6, sizes2, colors, styles)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvIncomeTitle.setOnClickListener(this)
    }

    override fun initData() {
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_incomeTitle -> {
                DialogHelp.Builder().setTitle(i18N(com.rt.base.R.string.说明))
                    .setContentMsg(i18N(com.rt.base.R.string.总营收包含被追缴金额和自主缴费不含追缴他人金额)).setCancelable(true)
                    .setIsCloseShow(true)
                    .setLeftMsg("")
                    .setRightMsg("")
                    .build(this@IncomeCountingActivity)
                    .showDailog()
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            errMsg.observe(this@IncomeCountingActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@IncomeCountingActivity){
                dismissProgressDialog()
            }
        }
    }

    override fun providerVMClass(): Class<IncomeCountingViewModel> {
        return IncomeCountingViewModel::class.java
    }

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityIncomeCountingBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

}