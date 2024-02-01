package com.rt.inspector.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.BaseApplication
import com.rt.base.dialog.VBBaseLibDialog
import com.rt.base.help.ActivityCacheManager
import com.rt.inspector.R
import com.rt.inspector.adapter.ViolationSelectAdapter
import com.rt.inspector.databinding.DialogViolationSelectBinding

class ViolationSelectDialog(var callBack: ViolationSelectCallBack) : VBBaseLibDialog<DialogViolationSelectBinding>(
    ActivityCacheManager.instance().getCurrentActivity()!!,
    com.rt.base.R.style.CommonBottomDialogStyle
), OnClickListener {
    var violationSelectAdapter: ViolationSelectAdapter? = null
    var selectViolation: String? = null
    var dataType = 0

    init {
        initView()
    }

    private fun initView() {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        binding.rtvOk.setOnClickListener(this)
    }

    fun setParkingLot(dataType: Int, parkingLotList: MutableList<String>, current: String) {
        setDialogHeight(parkingLotList.size)
        this.dataType = dataType
        binding.rvSelect.setHasFixedSize(true)
        binding.rvSelect.layoutManager = LinearLayoutManager(BaseApplication.instance())
        violationSelectAdapter =
            ViolationSelectAdapter(parkingLotList, current, object : ViolationSelectAdapter.ViolationSelectAdapterCallBack {
                override fun choose(violation: String) {
                    selectViolation = violation
                }
            })
        binding.rvSelect.adapter = violationSelectAdapter
    }

    fun setName(dataType: Int, nameList: MutableList<String>, current: String) {
        setDialogHeight(nameList.size)
        this.dataType = dataType
        binding.rvSelect.setHasFixedSize(true)
        binding.rvSelect.layoutManager = LinearLayoutManager(BaseApplication.instance())
        violationSelectAdapter =
            ViolationSelectAdapter(nameList, current, object : ViolationSelectAdapter.ViolationSelectAdapterCallBack {
                override fun choose(violation: String) {
                    selectViolation = violation
                }
            })
        binding.rvSelect.adapter = violationSelectAdapter
    }

    fun setType(dataType: Int, typeList: MutableList<String>, current: String) {
        setDialogHeight(typeList.size)
        this.dataType = dataType
        binding.rvSelect.setHasFixedSize(true)
        binding.rvSelect.layoutManager = LinearLayoutManager(BaseApplication.instance())
        violationSelectAdapter =
            ViolationSelectAdapter(typeList, current, object : ViolationSelectAdapter.ViolationSelectAdapterCallBack {
                override fun choose(violation: String) {
                    selectViolation = violation
                }
            })
        binding.rvSelect.adapter = violationSelectAdapter
    }

    fun setDialogHeight(size: Int) {
        var height = SizeUtils.dp2px(58f) * size + SizeUtils.dp2px(135f)
        if (height > ScreenUtils.getAppScreenHeight() - BarUtils.getStatusBarHeight()) {
            height = ScreenUtils.getAppScreenHeight() - BarUtils.getStatusBarHeight()
        }
        setLayout(ScreenUtils.getAppScreenWidth().toFloat(), height.toFloat())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rtv_ok -> {
                when (dataType) {
                    0 -> {
                        callBack.parkingLotChoose(selectViolation.toString())
                    }

                    1 -> {
                        callBack.nameChoose(selectViolation.toString())
                    }

                    2 -> {
                        callBack.typeChoose(selectViolation.toString())
                    }
                }
                dismiss()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding? {
        return DialogViolationSelectBinding.inflate(layoutInflater)
    }

    override fun getHideInput(): Boolean {
        return true
    }

    override fun getWidth(): Float {
        return WindowManager.LayoutParams.MATCH_PARENT.toFloat()
    }

    override fun getHeight(): Float {
        return WindowManager.LayoutParams.WRAP_CONTENT.toFloat()
    }

    override fun getCanceledOnTouchOutside(): Boolean {
        return true
    }

    override fun getGravity(): Int {
        return Gravity.BOTTOM
    }

    interface ViolationSelectCallBack {
        fun parkingLotChoose(parkingLot: String)
        fun nameChoose(name: String)
        fun typeChoose(type: String)
    }

}