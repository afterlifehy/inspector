package com.rt.inspector.adapter

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.BusinessLicenseBean
import com.rt.base.ext.i18n
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemBusinessLicenseBinding

class BusinessLicenseAdapter(data: MutableList<BusinessLicenseBean>? = null, val onClickListener: OnClickListener) :
    BaseBindingAdapter<BusinessLicenseBean, ItemBusinessLicenseBinding>(data) {
    val authMap: MutableMap<String, String> = ArrayMap()

    init {
        authMap["01"] = i18n(com.rt.base.R.string.已认证)
        authMap["02"] = i18n(com.rt.base.R.string.未认证)
    }

    override fun convert(holder: VBViewHolder<ItemBusinessLicenseBinding>, item: BusinessLicenseBean) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.llBusinessLicense.layoutParams = lp

        holder.vb.tvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        holder.vb.tvStreetName.text = item.streetName
        holder.vb.tvEnterprise.text = item.companyName
        holder.vb.tvAuth.text = authMap[item.authentication]
        holder.vb.tvDueDate.text = item.expirationTime

        holder.vb.llBusinessLicense.tag = item
        holder.vb.llBusinessLicense.setOnClickListener(onClickListener)
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemBusinessLicenseBinding {
        return ItemBusinessLicenseBinding.inflate(inflater)
    }
}