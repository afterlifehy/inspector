package com.rt.inspector.ui.activity.violation

import android.content.DialogInterface
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityAssistantViolationReportBinding
import com.rt.inspector.dialog.ViolationSelectDialog
import com.rt.inspector.mvvm.viewmodel.AssistantViolationReportViewModel

@Route(path = ARouterMap.ASSISTANT_VIOLATION_REPORT)
class AssistantViolationReportActivity : VbBaseActivity<AssistantViolationReportViewModel, ActivityAssistantViolationReportBinding>(),
    OnClickListener {
    var violationSelectDialog: ViolationSelectDialog? = null
    var parkingLotList: MutableList<String> = ArrayList()
    var nameList: MutableList<String> = ArrayList()
    var typeList: MutableList<String> = ArrayList()
    var currentParkingLot = ""
    var currentName = ""
    var currentType = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.协管员违规上报)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvParkingLot.setOnClickListener(this)
        binding.tvName.setOnClickListener(this)
        binding.tvType.setOnClickListener(this)
        binding.ivImg1.setOnClickListener(this)
        binding.ivImg2.setOnClickListener(this)
        binding.ivImg3.setOnClickListener(this)
        binding.rflReport.setOnClickListener(this)
    }

    override fun initData() {
        parkingLotList.add("1")
        parkingLotList.add("2")
        parkingLotList.add("3")
        parkingLotList.add("4")
        parkingLotList.add("5")
        parkingLotList.add("6")
        parkingLotList.add("7")
        parkingLotList.add("8")
        parkingLotList.add("9")
        parkingLotList.add("10")

        nameList.add("1")
        nameList.add("2")
        nameList.add("3")
        nameList.add("4")
        nameList.add("5")

        typeList.add("1")
        typeList.add("2")
        typeList.add("3")
        typeList.add("4")
        typeList.add("5")
        typeList.add("6")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_parkingLot -> {
                violationSelectDialog = ViolationSelectDialog(object : ViolationSelectDialog.ViolationSelectCallBack {
                    override fun parkingLotChoose(parkingLot: String) {
                        binding.tvParkingLot.text = parkingLot
                        currentParkingLot = parkingLot
                    }

                    override fun nameChoose(name: String) {
                    }

                    override fun typeChoose(type: String) {
                    }
                })
                violationSelectDialog?.setParkingLot(0, parkingLotList, currentParkingLot)
                violationSelectDialog?.show()
                arrowChange(binding.tvParkingLot)
            }

            R.id.tv_name -> {
                violationSelectDialog = ViolationSelectDialog(object : ViolationSelectDialog.ViolationSelectCallBack {

                    override fun parkingLotChoose(parkingLot: String) {

                    }

                    override fun nameChoose(name: String) {
                        binding.tvName.text = name
                        currentName = name
                    }

                    override fun typeChoose(type: String) {
                    }
                })
                violationSelectDialog?.setName(1, nameList, currentName)
                violationSelectDialog?.show()
                arrowChange(binding.tvName)
            }

            R.id.tv_type -> {
                violationSelectDialog = ViolationSelectDialog(object : ViolationSelectDialog.ViolationSelectCallBack {
                    override fun parkingLotChoose(parkingLot: String) {
                    }

                    override fun nameChoose(name: String) {
                    }

                    override fun typeChoose(type: String) {
                        binding.tvType.text = type
                        currentType = type
                    }
                })
                violationSelectDialog?.setType(2, typeList, currentType)
                violationSelectDialog?.show()
                arrowChange(binding.tvType)
            }

            R.id.iv_img1 -> {

            }

            R.id.iv_img2 -> {

            }

            R.id.iv_img3 -> {

            }

            R.id.rfl_report -> {

            }
        }
    }

    fun arrowChange(view: AppCompatTextView) {
        val upDrawable = ContextCompat.getDrawable(BaseApplication.instance(), com.rt.common.R.mipmap.ic_violation_arrow_up)
        upDrawable?.setBounds(0, 0, upDrawable.intrinsicWidth, upDrawable.intrinsicHeight)
        view.setCompoundDrawables(
            null,
            null,
            upDrawable,
            null
        )
        violationSelectDialog?.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(dialog: DialogInterface?) {
                val downDrawable =
                    ContextCompat.getDrawable(BaseApplication.instance(), com.rt.common.R.mipmap.ic_violation_arrow_down)
                downDrawable?.setBounds(0, 0, downDrawable.intrinsicWidth, downDrawable.intrinsicHeight)
                view.setCompoundDrawables(
                    null,
                    null,
                    downDrawable,
                    null
                )
            }
        })
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityAssistantViolationReportBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<AssistantViolationReportViewModel> {
        return AssistantViolationReportViewModel::class.java
    }
}