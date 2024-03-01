package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.EnterpriseBean
import com.rt.inspector.databinding.ItemViolationSelectBinding

class EnterpriseSelectAdapter(data: MutableList<EnterpriseBean>? = null, var current: EnterpriseBean?, val callback: EnterpriseSelectAdapterCallBack) :
    BaseBindingAdapter<EnterpriseBean, ItemViolationSelectBinding>(data) {
    var lastViolationCB: CheckBox? = null
    var currentViolationCB: CheckBox? = null
    override fun convert(holder: VBViewHolder<ItemViolationSelectBinding>, item: EnterpriseBean) {
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            SizeUtils.dp2px(58f)
        )
        holder.vb.rlViolation.layoutParams = lp

        holder.vb.tvViolation.text = item.companyName
        if (current == item) {
            holder.vb.cbViolation.isChecked = true
            currentViolationCB = holder.vb.cbViolation
        } else {
            holder.vb.cbViolation.isChecked = false
        }
        holder.vb.rlViolation.setOnClickListener {
            lastViolationCB = currentViolationCB
            lastViolationCB?.isChecked = false
            currentViolationCB = holder.vb.cbViolation
            currentViolationCB?.isChecked = true
            current = item
            callback.choose(current)
        }
        holder.vb.cbViolation.setOnClickListener {
            lastViolationCB = currentViolationCB
            lastViolationCB?.isChecked = false
            currentViolationCB = holder.vb.cbViolation
            currentViolationCB?.isChecked = true
            current = item
            callback.choose(current)
        }
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemViolationSelectBinding {
        return ItemViolationSelectBinding.inflate(inflater)
    }

    interface EnterpriseSelectAdapterCallBack {
        fun choose(enterprise: EnterpriseBean?)
    }
}