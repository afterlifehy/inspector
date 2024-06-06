package com.rt.inspector.adapter

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.QueryParkingInfoBean
import com.rt.base.ext.i18n
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemParkingInfoBinding

class ParkingInfoAdapter(data: MutableList<QueryParkingInfoBean>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<QueryParkingInfoBean, ItemParkingInfoBinding>(data) {
    var isUserMap: MutableMap<String, String> = ArrayMap()

    init {
        isUserMap["0"] = "否"
        isUserMap["1"] = "是"
    }

    override fun convert(holder: VBViewHolder<ItemParkingInfoBinding>, item: QueryParkingInfoBean) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llHistory.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.tvName.text = item.driverName + "-" + item.phone
        holder.vb.tvPlateId.text = item.carLicense
        holder.vb.tvUser.text = i18n(com.rt.base.R.string.普通用户)
        if (item.startTime.isNotEmpty()) {
            holder.vb.tvTime.text = item.startTime + "~" + item.endTime
        } else {
            holder.vb.tvTime.text = ""
        }

        holder.vb.llHistory.tag = item
        holder.vb.llHistory.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemParkingInfoBinding {
        return ItemParkingInfoBinding.inflate(inflater)
    }
}