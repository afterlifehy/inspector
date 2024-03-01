package com.rt.inspector.adapter

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.AssistantViolationHistoryBean
import com.rt.base.ext.i18n
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemAssistantViolationHistoryBinding

class AssistantViolationHistoryAdapter(data: MutableList<AssistantViolationHistoryBean>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<AssistantViolationHistoryBean, ItemAssistantViolationHistoryBinding>(data) {
    var reasonMap: MutableMap<String, String> = ArrayMap()
    var stateMap: MutableMap<String, String> = ArrayMap()

    init {
        reasonMap["01"] = i18n(com.rt.base.R.string.工作范围)
        reasonMap["02"] = i18n(com.rt.base.R.string.超时未报)
        reasonMap["03"] = i18n(com.rt.base.R.string.连续违规)

        stateMap["01"] = i18n(com.rt.base.R.string.未处理)
        stateMap["02"] = i18n(com.rt.base.R.string.已解决)
        stateMap["03"] = i18n(com.rt.base.R.string.处理中)
    }

    override fun convert(holder: VBViewHolder<ItemAssistantViolationHistoryBinding>, item: AssistantViolationHistoryBean) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llHistory.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.tvName.text = item.managerName + "-" + item.adminAccount
        holder.vb.tvStreetName.text = item.streetName
        holder.vb.tvReason.text = reasonMap[item.type]
        holder.vb.tvTime.text = item.reportTime
        holder.vb.tvStatus.text = stateMap[item.state]
        holder.vb.llHistory.tag = item
        holder.vb.llHistory.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemAssistantViolationHistoryBinding {
        return ItemAssistantViolationHistoryBinding.inflate(inflater)
    }
}