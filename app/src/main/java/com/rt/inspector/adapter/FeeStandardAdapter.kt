package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.ext.gone
import com.rt.base.ext.show
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemFeeStandardBinding

class FeeStandardAdapter(data: MutableList<Int>? = null) : BaseBindingAdapter<Int, ItemFeeStandardBinding>(data) {
    var callback: ExpandFeeStandardCallback? = null
    override fun convert(holder: VBViewHolder<ItemFeeStandardBinding>, item: Int) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.flFeeStandard.layoutParams = lp

        holder.vb.rtvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.rlFeeStandard.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                if (holder.vb.cbFeeStandard.isChecked) {
                    holder.vb.cbFeeStandard.isChecked = false
                    holder.vb.rllNonHigh.gone()
                } else {
                    holder.vb.cbFeeStandard.isChecked = true
                    holder.vb.rllNonHigh.show()
                    callback?.expand(item)
                }
            }
        })
        holder.vb.cbFeeStandard.setOnCheckedChangeListener(object : OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    holder.vb.rllNonHigh.show()
                    callback?.expand(item)
                } else {
                    holder.vb.rllNonHigh.gone()
                }
            }
        })
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemFeeStandardBinding {
        return ItemFeeStandardBinding.inflate(inflater)
    }

    fun setExpandCallback(callBack: ExpandFeeStandardCallback) {
        this.callback = callBack
    }

    interface ExpandFeeStandardCallback {
        fun expand(street: Int)
    }
}