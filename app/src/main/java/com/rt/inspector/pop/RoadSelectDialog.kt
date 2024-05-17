package com.rt.inspector.pop

import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.BaseApplication
import com.rt.base.bean.Road
import com.rt.base.dialog.VBBaseLibDialog
import com.rt.base.help.ActivityCacheManager
import com.rt.inspector.R
import com.rt.inspector.adapter.RoadUnbindListAdapter
import com.rt.inspector.databinding.DialogRoadSelectBinding

class RoadSelectDialog(
    var roadUnBindList: MutableList<Road>,
    var choosedList: MutableList<Road>,
    val callback: RoadChooseCallBack
) : VBBaseLibDialog<DialogRoadSelectBinding>(
    ActivityCacheManager.instance().getCurrentActivity()!!,
    com.rt.base.R.style.CommonBottomDialogStyle
), View.OnClickListener {
    var roadUnbindListAdapter: RoadUnbindListAdapter? = null

    init {
        initView()
    }

    private fun initView() {
        var height = (SizeUtils.dp2px(55f) * (roadUnBindList.size + 0.5) + SizeUtils.dp2px(10f)).toInt()
        if (height > ScreenUtils.getAppScreenHeight() - BarUtils.getStatusBarHeight()) {
            height = ScreenUtils.getAppScreenHeight() - BarUtils.getStatusBarHeight()
        }
        setLayout(ScreenUtils.getAppScreenWidth().toFloat(), height.toFloat())
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.rvRoad.setHasFixedSize(true)
        binding.rvRoad.layoutManager = LinearLayoutManager(BaseApplication.instance())
        roadUnbindListAdapter = RoadUnbindListAdapter(roadUnBindList, choosedList)
        binding.rvRoad.adapter = roadUnbindListAdapter

        binding.rtvOk.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rtv_ok -> {
                callback.chooseRoad()
                dismiss()
            }
        }
    }

    interface RoadChooseCallBack {
        fun chooseRoad()
    }

    override fun getVbBindingView(): ViewBinding {
        return DialogRoadSelectBinding.inflate(layoutInflater)
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
}