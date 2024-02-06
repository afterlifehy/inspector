package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.inspector.databinding.ItemTaskReceptionBinding

class TaskReceptionAdapter(data: MutableList<Int>? = null,val onClickListener: OnClickListener) : BaseBindingAdapter<Int, ItemTaskReceptionBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemTaskReceptionBinding>, item: Int) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.rllTask.layoutParams = lp

        holder.vb.rllTask.tag = item
        holder.vb.rllTask.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemTaskReceptionBinding {
        return ItemTaskReceptionBinding.inflate(inflater)
    }
}