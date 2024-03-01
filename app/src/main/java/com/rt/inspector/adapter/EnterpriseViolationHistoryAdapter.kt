package com.rt.inspector.adapter

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.EnterpriseViolationHistoryBean
import com.rt.base.ext.i18n
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemEnterpriseViolationHistoryBinding

class EnterpriseViolationHistoryAdapter(data: MutableList<EnterpriseViolationHistoryBean>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<EnterpriseViolationHistoryBean, ItemEnterpriseViolationHistoryBinding>(data) {
    var reasonMap: MutableMap<String, String> = ArrayMap()
    var stateMap: MutableMap<String, String> = ArrayMap()

    init {
        reasonMap["01"] = i18n(com.rt.base.R.string.使用私人二维码收费)

        stateMap["01"] = i18n(com.rt.base.R.string.未处理)
        stateMap["02"] = i18n(com.rt.base.R.string.已解决)
        stateMap["03"] = i18n(com.rt.base.R.string.处理中)
    }
    override fun convert(holder: VBViewHolder<ItemEnterpriseViolationHistoryBinding>, item: EnterpriseViolationHistoryBean) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llHistory.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.tvName.text = item.companyName
        holder.vb.tvReason.text = reasonMap[item.type]
        holder.vb.tvTime.text = item.reportTime
        holder.vb.tvStatus.text = stateMap[item.state]
        holder.vb.llHistory.tag = item
        holder.vb.llHistory.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemEnterpriseViolationHistoryBinding {
        return ItemEnterpriseViolationHistoryBinding.inflate(inflater)
    }
}