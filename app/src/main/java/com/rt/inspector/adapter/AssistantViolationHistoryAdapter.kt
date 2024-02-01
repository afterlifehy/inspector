package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemAssistantViolationHistoryBinding

class AssistantViolationHistoryAdapter(data: MutableList<Int>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<Int, ItemAssistantViolationHistoryBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemAssistantViolationHistoryBinding>, item: Int) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llHistory.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.llHistory.tag = item
        holder.vb.llHistory.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemAssistantViolationHistoryBinding {
        return ItemAssistantViolationHistoryBinding.inflate(inflater)
    }
}