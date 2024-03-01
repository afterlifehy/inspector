package com.rt.inspector.adapter

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.TaskBean
import com.rt.base.ext.i18n
import com.rt.inspector.databinding.ItemTaskReceptionBinding

class TaskReceptionAdapter(data: MutableList<TaskBean>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<TaskBean, ItemTaskReceptionBinding>(data) {
    val taskStateMap: MutableMap<String, String> = ArrayMap()

    init {
        taskStateMap["1"] = i18n(com.rt.base.R.string.泊位异常)
        taskStateMap["2"] = i18n(com.rt.base.R.string.人员异常)
        taskStateMap["3"] = i18n(com.rt.base.R.string.停车异常)
        taskStateMap["4"] = i18n(com.rt.base.R.string.其他)
    }

    override fun convert(holder: VBViewHolder<ItemTaskReceptionBinding>, item: TaskBean) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.rllTask.layoutParams = lp

        holder.vb.tvTask.text = item.notificationTitle
        holder.vb.rllTask.tag = item
        holder.vb.rllTask.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemTaskReceptionBinding {
        return ItemTaskReceptionBinding.inflate(inflater)
    }
}