package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.inspector.databinding.ItemRoadBindBinding

class RoadBindAdapter(data: MutableList<Int>? = null) : BaseBindingAdapter<Int, ItemRoadBindBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemRoadBindBinding>, item: Int) {
        holder.vb.tvStreetNo.text = "JAZ020_2"
        holder.vb.tvStreetName.text = "昌平路(西康路-常德路)"
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemRoadBindBinding {
        return ItemRoadBindBinding.inflate(inflater)
    }
}