package com.rt.inspector.dialog

import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.view.WindowManager
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ScreenUtils
import com.rt.base.dialog.VBBaseLibDialog
import com.rt.base.help.ActivityCacheManager
import com.rt.inspector.R
import com.rt.inspector.databinding.DialogSearchBinding

class SearchDialog(var hintTxt: String, var callback: SearchCallBack) :
    VBBaseLibDialog<DialogSearchBinding>(ActivityCacheManager.instance().getCurrentActivity()!!),
    OnClickListener {

    init {
        initView()
    }

    private fun initView() {
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        binding.retSearch.hint = hintTxt
        binding.rtvCancel.setOnClickListener(this)
        binding.rtvConfirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rtv_cancel -> {
                dismiss()
            }

            R.id.rtv_confirm -> {
                callback.confirm(binding.retSearch.text.toString())
                dismiss()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return DialogSearchBinding.inflate(layoutInflater)
    }

    override fun getHideInput(): Boolean {
        return true
    }

    override fun getWidth(): Float {
        return ScreenUtils.getScreenWidth() * 0.9f
    }

    override fun getHeight(): Float {
        return WindowManager.LayoutParams.WRAP_CONTENT.toFloat()
    }

    override fun getCanceledOnTouchOutside(): Boolean {
        return true
    }

    override fun getGravity(): Int {
        return Gravity.CENTER
    }

    interface SearchCallBack {
        fun confirm(queryTxt: String)
    }
}