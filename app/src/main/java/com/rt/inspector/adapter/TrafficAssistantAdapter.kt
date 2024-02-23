package com.rt.inspector.adapter

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.TrafficAssistantBean
import com.rt.base.ext.i18n
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemTrafficAssistantBinding

class TrafficAssistantAdapter(data: MutableList<TrafficAssistantBean>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<TrafficAssistantBean, ItemTrafficAssistantBinding>(data) {
    val onStateMap: MutableMap<String, String> = ArrayMap()
    val offStateMap: MutableMap<String, String> = ArrayMap()

    init {
        onStateMap["00"] = i18n(com.rt.base.R.string.正常)
        onStateMap["01"] = i18n(com.rt.base.R.string.迟到)
        onStateMap["02"] = i18n(com.rt.base.R.string.早退)

        offStateMap["00"] = i18n(com.rt.base.R.string.正常)
        offStateMap["01"] = i18n(com.rt.base.R.string.上班超范围)
        offStateMap["02"] = i18n(com.rt.base.R.string.下班超范围)
        offStateMap["03"] = i18n(com.rt.base.R.string.上下班都超范围)
    }

    override fun convert(holder: VBViewHolder<ItemTrafficAssistantBinding>, item: TrafficAssistantBean) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llTrafficAssistant.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.tvStreetName.text = item.streetName
        holder.vb.tvTrafficAssistantName.text = item.managerName
        holder.vb.tvWorkHours.text = item.beginTime
        holder.vb.tvOffWorkHours.text = item.endTime
        holder.vb.tvWorkAttendance.text = onStateMap[item.onState]
        holder.vb.tvOffWorkAttendance.text = offStateMap[item.offState]

        holder.vb.llTrafficAssistant.tag = item
        holder.vb.llTrafficAssistant.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemTrafficAssistantBinding {
        return ItemTrafficAssistantBinding.inflate(inflater)
    }
}