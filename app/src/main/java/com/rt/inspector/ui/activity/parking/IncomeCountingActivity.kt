package com.rt.inspector.ui.activity.parking

import android.content.Intent
import android.provider.Settings
import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.dialog.DialogHelp
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18N
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.AppUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityIncomeCountingBinding
import com.rt.inspector.mvvm.viewmodel.IncomeCountingViewModel
import com.zrq.spanbuilder.TextStyle
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.INCOME_COUNTING)
class IncomeCountingActivity : VbBaseActivity<IncomeCountingViewModel, ActivityIncomeCountingBinding>(), OnClickListener {
    val sizes = intArrayOf(33, 13)
    val colors = intArrayOf(com.rt.base.R.color.color_fff15a24, com.rt.base.R.color.color_ff1a1a1a)
    val sizes2 = intArrayOf(17, 17)
    val styles = arrayOf(TextStyle.BOLD, TextStyle.NORMAL)
    var streetNo = ""
    var loginName = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18N(com.rt.base.R.string.营收盘点)

        streetNo = intent.getStringExtra(ARouterMap.INCOME_COUNTING_STREET_NO).toString()
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvIncomeTitle.setOnClickListener(this)
    }

    override fun initData() {
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            val param = HashMap<String, Any>()
            val jsonobject = JSONObject()
            jsonobject["loginName"] = loginName
            jsonobject["streetNo"] = streetNo
            param["attr"] = jsonobject
            mViewModel.incomeCounting(param)
        }
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
            incomeCountingLiveData.observe(this@IncomeCountingActivity) {
                dismissProgressDialog()
                val strings = arrayOf(it.orderTotal.toString(), "笔")
                binding.tvOrderCount.text = AppUtil.getSpan(strings, sizes, colors, styles)
                val strings2 = arrayOf(it.totalAmounts, "元")
                binding.tvIncome.text = AppUtil.getSpan(strings2, sizes, colors, styles)
                binding.tvRefusePayCount.text = "${it.refusePayCount}笔"
                binding.tvPartPayCount.text = "${it.partPayCount}笔"
                val strings3 = arrayOf(it.beRecoveredMoney, "元")
                binding.tvRecovered.text = AppUtil.getSpan(strings3, sizes2, colors, styles)
                val strings4 = arrayOf(it.totalRecoveredMoney, "元")
                binding.tvRecoveryOthers.text = AppUtil.getSpan(strings4, sizes2, colors, styles)
                val strings5 = arrayOf(it.autonomousPayCount, "元")
                binding.tvSelfRecovery.text = AppUtil.getSpan(strings5, sizes2, colors, styles)
                val strings6 = arrayOf(it.totalMoney, "元")
                binding.tvReceiveAmount.text = AppUtil.getSpan(strings6, sizes2, colors, styles)
            }
            errMsg.observe(this@IncomeCountingActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@IncomeCountingActivity) {
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