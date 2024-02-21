package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.ParkingManagementBean
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemParkingManagementBinding

class ParkingManagementAdapter(data: MutableList<ParkingManagementBean>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<ParkingManagementBean, ItemParkingManagementBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemParkingManagementBinding>, item: ParkingManagementBean) {
        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        if (item.adminNames.isNotEmpty()) {
            holder.vb.tvStreetNo.text = item.streetNo + "-" + item.adminNames[0]
        } else {
            holder.vb.tvStreetNo.text = item.streetNo
        }
        holder.vb.tvStreetName.text = item.streetName
        holder.vb.tvReceivable.text = item.orderAmount
        holder.vb.tvActualReceipt.text = item.actualAmount
        holder.vb.rllParking.tag = item
        holder.vb.rllParking.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemParkingManagementBinding {
        return ItemParkingManagementBinding.inflate(inflater)
    }
}