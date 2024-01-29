package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemParkingManagementBinding

class ParkingManagementAdapter(data: MutableList<Int>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<Int, ItemParkingManagementBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemParkingManagementBinding>, item: Int) {
        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.rllParking.tag = item
        holder.vb.rllParking.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemParkingManagementBinding {
        return ItemParkingManagementBinding.inflate(inflater)
    }
}