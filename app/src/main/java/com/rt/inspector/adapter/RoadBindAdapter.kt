package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.Road
import com.rt.inspector.databinding.ItemRoadBindBinding

class RoadBindAdapter(data: MutableList<Road>? = null) : BaseBindingAdapter<Road, ItemRoadBindBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemRoadBindBinding>, item: Road) {
        holder.vb.tvStreetNo.text = item.streetNo
        holder.vb.tvStreetName.text = item.streetName
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemRoadBindBinding {
        return ItemRoadBindBinding.inflate(inflater)
    }
}