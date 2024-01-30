package com.rt.base.dialog

import android.content.Context
import android.text.TextUtils
import android.view.*
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ScreenUtils
import com.rt.base.R
import com.rt.base.databinding.DialogContextLayoutBinding
import com.rt.base.ext.gone
import com.rt.base.ext.show

class GlobalDialog(context: Context, mDialogHelp: DialogHelp) :
    VBBaseLibDialog<DialogContextLayoutBinding>(context), View.OnClickListener {
    private var mRootView: View? = null
    private var mDialogHelp: DialogHelp? = null

    init {
        this.mDialogHelp = mDialogHelp
        initView()
    }

    private fun initView() {
        bindShowViewData()

        binding.rtvLeft.setOnClickListener(this)
        binding.rtvRight.setOnClickListener(this)
        binding.ivClose.setOnClickListener(this)
    }

    fun bindShowViewData() {
        mDialogHelp?.let {
            if (!TextUtils.isEmpty(it.title)) {
                binding.tvTitle.text = it.title
            } else {
                binding.tvTitle.gone()
            }
            if (!TextUtils.isEmpty(it.contentMsg)) {
                binding.tvContent.text = it.contentMsg
            } else {
                binding.tvContent.gone()
            }

            if (it.isAloneButton) {
                binding.rtvLeft.gone()
                binding.rtvRight.text = it.rightMsg
            } else {
                if (it.leftMsg.isEmpty()) {
                    binding.rtvLeft.gone()
                } else {
                    binding.rtvLeft.text = it.leftMsg
                }
            }
            if (it.rightMsg.isEmpty()) {
                binding.rtvRight.gone()
            } else {
                binding.rtvRight.text = it.rightMsg
            }
            setCancelable(it.cancelable)
            if (it.isCloseShow) {
                binding.ivClose.show()
            } else {
                binding.ivClose.gone()
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.rtv_left -> {
                mDialogHelp?.mOnButtonClickLinsener?.onLeftClickLinsener()
                if (mDialogHelp!!.cancelable) {
                    dismiss()
                }

            }

            R.id.rtv_right -> {
                mDialogHelp?.mOnButtonClickLinsener?.onRightClickLinsener()
                if (mDialogHelp!!.cancelable) {
                    dismiss()
                }
            }

            R.id.iv_close -> {
                dismiss()
            }
        }
    }

    override fun getHideInput(): Boolean {
        return true
    }

    override fun getWidth(): Float {
        return ScreenUtils.getScreenWidth() * 0.85f
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

    override fun getVbBindingView(): ViewBinding {
        return DialogContextLayoutBinding.inflate(layoutInflater)
    }
}