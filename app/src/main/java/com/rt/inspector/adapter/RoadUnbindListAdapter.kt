package com.rt.inspector.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.Road
import com.rt.base.ext.hide
import com.rt.base.ext.show
import com.rt.inspector.databinding.ItemRoadUnbindBinding

class RoadUnbindListAdapter(data: MutableList<Road>? = null, var choosedList: MutableList<Road>) :
    BaseBindingAdapter<Road, ItemRoadUnbindBinding>(data) {
    override fun convert(holder: VBViewHolder<ItemRoadUnbindBinding>, item: Road) {
        if (item.streetName.isNotEmpty()) {
            holder.vb.rlRoad.show()
            holder.vb.tvStreet.text = item.streetName
            if (choosedList.contains(item)) {
                holder.vb.cbStreet.isChecked = true
            } else {
                holder.vb.cbStreet.isChecked = false
            }
            holder.vb.rlRoad.setOnClickListener {
                holder.vb.cbStreet.isChecked = !item.ischeck
                item.ischeck = holder.vb.cbStreet.isChecked
                if (holder.vb.cbStreet.isChecked) {
                    choosedList.add(item)
                } else {
                    choosedList.remove(item)
                }
            }
            holder.vb.cbStreet.setOnClickListener {
                if (holder.vb.cbStreet.isChecked) {
                    item.ischeck = true
                    choosedList.add(item)
                } else {
                    item.ischeck = false
                    choosedList.remove(item)
                }
            }
        } else {
            holder.vb.rlRoad.hide()
        }
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemRoadUnbindBinding {
        val lp = ViewGroup.MarginLayoutParams(WindowManager.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(55f))
        val binding = ItemRoadUnbindBinding.inflate(inflater)
        binding.root.layoutParams = lp
        return binding
    }
}