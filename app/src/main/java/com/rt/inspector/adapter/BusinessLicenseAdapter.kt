package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemBusinessLicenseBinding

class BusinessLicenseAdapter(data: MutableList<Int>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<Int, ItemBusinessLicenseBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemBusinessLicenseBinding>, item: Int) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llBusinessLicense.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.llBusinessLicense.tag = item
        holder.vb.llBusinessLicense.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemBusinessLicenseBinding {
        return ItemBusinessLicenseBinding.inflate(inflater)
    }
}