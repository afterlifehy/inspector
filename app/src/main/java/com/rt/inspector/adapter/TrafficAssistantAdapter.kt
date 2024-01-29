package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemTrafficAssistantBinding

class TrafficAssistantAdapter(data: MutableList<Int>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<Int, ItemTrafficAssistantBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemTrafficAssistantBinding>, item: Int) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llTrafficAssistant.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.llTrafficAssistant.tag = item
        holder.vb.llTrafficAssistant.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemTrafficAssistantBinding {
        return ItemTrafficAssistantBinding.inflate(inflater)
    }
}