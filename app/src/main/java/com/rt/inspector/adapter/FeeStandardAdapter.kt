package com.rt.inspector.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.CompoundButton.OnCheckedChangeListener
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.adapter.BaseBindingAdapter
import com.rt.base.adapter.VBViewHolder
import com.rt.base.bean.FeeStandardBean
import com.rt.base.bean.Street
import com.rt.base.ext.gone
import com.rt.base.ext.i18n
import com.rt.base.ext.show
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ItemFeeStandardBinding

class FeeStandardAdapter(data: MutableList<FeeStandardBean>? = null) : BaseBindingAdapter<FeeStandardBean, ItemFeeStandardBinding>(data) {
    var callback: ExpandFeeStandardCallback? = null
    val colors = intArrayOf(com.rt.base.R.color.color_ff1a1a1a, com.rt.base.R.color.color_ff1a1a1a)
    val sizes = intArrayOf(17, 13)

    @SuppressLint("SetTextI18n")
    override fun convert(holder: VBViewHolder<ItemFeeStandardBinding>, item: FeeStandardBean) {
        val lp = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        lp.bottomMargin = SizeUtils.dp2px(13f)
        holder.vb.flFeeStandard.layoutParams = lp

        holder.vb.rtvNum.text = AppUtil.fillZero((data.indexOf(item) + 1).toString())
        val street = item.street
        holder.vb.tvStreetName.text = street!!.streetName
        holder.vb.cbFeeStandard.setOnCheckedChangeListener(null)
        holder.vb.cbFeeStandard.isChecked = item.isExpand
        if (item.isExpand && item.feeStandardNotHighBean != null) {
            holder.vb.rllNonHigh.show()
            Log.v("1234", "11   " + street.streetName + item.isExpand.toString())
            val feeStandardNotHighBean = item.feeStandardNotHighBean!!
            val strings = arrayOf(i18n(com.rt.base.R.string.工作日) + "\n", feeStandardNotHighBean.timing.duration[0])
            holder.vb.tvWorkDay.text = AppUtil.getSpan(strings, sizes, colors)
            val strings2 = arrayOf(i18n(com.rt.base.R.string.周末) + "\n", feeStandardNotHighBean.timing.duration[1])
            holder.vb.tvWeekend.text = AppUtil.getSpan(strings2, sizes, colors)
            val strings3 = arrayOf(i18n(com.rt.base.R.string.节假日) + "\n", feeStandardNotHighBean.timing.duration[2])
            holder.vb.tvHoliday.text = AppUtil.getSpan(strings3, sizes, colors)

            holder.vb.tvWorkDayFee1.text = feeStandardNotHighBean.timing.hourMoney[0] + "元"
            holder.vb.tvWeekendFee1.text = feeStandardNotHighBean.timing.hourMoney[1] + "元"
            holder.vb.tvHolidayFee1.text = feeStandardNotHighBean.timing.hourMoney[2] + "元"

            holder.vb.tvWorkDayFee2.text = feeStandardNotHighBean.timing.halfMoney[0] + "元"
            holder.vb.tvWeekendFee2.text = feeStandardNotHighBean.timing.halfMoney[1] + "元"
            holder.vb.tvHolidayFee2.text = feeStandardNotHighBean.timing.halfMoney[2] + "元"

            val strings4 = arrayOf(i18n(com.rt.base.R.string.工作日) + "\n", feeStandardNotHighBean.count.duration[0])
            holder.vb.tvWorkDayNight.text = AppUtil.getSpan(strings4, sizes, colors)
            val strings5 = arrayOf(i18n(com.rt.base.R.string.周末) + "\n", feeStandardNotHighBean.count.duration[1])
            holder.vb.tvWeekendNight.text = AppUtil.getSpan(strings5, sizes, colors)
            val strings6 = arrayOf(i18n(com.rt.base.R.string.节假日) + "\n", feeStandardNotHighBean.count.duration[2])
            holder.vb.tvHolidayNight.text = AppUtil.getSpan(strings6, sizes, colors)

            holder.vb.tvWorkDayFee3.text = feeStandardNotHighBean.count.money[0] + "元"
            holder.vb.tvWeekendFee3.text = feeStandardNotHighBean.count.money[1] + "元"
            holder.vb.tvHolidayFee3.text = feeStandardNotHighBean.count.money[2] + "元"
        } else if (item.isExpand && item.feeStandardHighResultBean != null) {
            holder.vb.rllHigh.show()
            Log.v("1234", "22   " + street.streetName + item.isExpand.toString())
            val feeStandardHighResultBean = item.feeStandardHighResultBean!!
            holder.vb.tvWorkDayTime.text =
                feeStandardHighResultBean.result[0].whiteStart + "-" + feeStandardHighResultBean.result[0].whiteEnd
            holder.vb.tvWorkNightTime.text =
                feeStandardHighResultBean.result[0].blackStart + "-次日" + feeStandardHighResultBean.result[0].blackEnd
            holder.vb.tvWorkDayStart15.text = feeStandardHighResultBean.result[0].first + "元"
            holder.vb.tvWorkDayEnd15.text = feeStandardHighResultBean.result[0].second + "元"
            holder.vb.tvWorkDayEnd30.text = feeStandardHighResultBean.result[0].third + "元"

            holder.vb.tvWeekendDayTime.text =
                feeStandardHighResultBean.result[1].whiteStart + "-" + feeStandardHighResultBean.result[1].whiteEnd
            holder.vb.tvWeekendNightTime.text =
                feeStandardHighResultBean.result[1].blackStart + "-次日" + feeStandardHighResultBean.result[1].blackEnd
            holder.vb.tvWeekendStart15.text = feeStandardHighResultBean.result[1].first + "元"
            holder.vb.tvWeekendEnd15.text = feeStandardHighResultBean.result[1].second + "元"
            holder.vb.tvWeekendEnd30.text = feeStandardHighResultBean.result[1].third + "元"

            holder.vb.tvWorkDayTime.text =
                feeStandardHighResultBean.result[2].whiteStart + "-" + feeStandardHighResultBean.result[2].whiteEnd
            holder.vb.tvWorkNightTime.text =
                feeStandardHighResultBean.result[2].blackStart + "-次日" + feeStandardHighResultBean.result[2].blackEnd
            holder.vb.tvHolidayStart15.text = feeStandardHighResultBean.result[2].first + "元"
            holder.vb.tvHolidayEnd15.text = feeStandardHighResultBean.result[2].second + "元"
            holder.vb.tvHolidayEnd30.text = feeStandardHighResultBean.result[2].third + "元"
        } else {
            holder.vb.rllHigh.gone()
            holder.vb.rllNonHigh.gone()
            holder.vb.cbFeeStandard.isChecked = false
        }
        holder.vb.rlFeeStandard.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                if (holder.vb.cbFeeStandard.isChecked) {
                    holder.vb.cbFeeStandard.isChecked = false
                    item.isExpand = false
                    if (item.street!!.parkingType == "1") {
                        holder.vb.rllHigh.gone()
                    } else {
                        holder.vb.rllNonHigh.gone()
                    }
                    Log.v("1234", "3   " + item.street!!.streetName + item.isExpand.toString())
                } else {
                    holder.vb.cbFeeStandard.isChecked = true
                    item.isExpand = true
                    if (item.street!!.parkingType == "1") {
                        holder.vb.rllHigh.show()
                    } else {
                        holder.vb.rllNonHigh.show()
                    }
                    callback?.expand(item)
                    Log.v("1234", "4   " + item.street!!.streetName + item.isExpand.toString())
                }
            }
        })
        holder.vb.cbFeeStandard.setOnCheckedChangeListener(object : OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    if (item.street!!.parkingType == "1") {
                        holder.vb.rllHigh.show()
                    } else {
                        holder.vb.rllNonHigh.show()
                    }
                    item.isExpand = true
                    Log.v("1234", "1   " + item.street!!.streetName + item.isExpand.toString())
                    callback?.expand(item)
                } else {
                    holder.vb.rllHigh.gone()
                    holder.vb.rllNonHigh.gone()
                    item.isExpand = false
                    Log.v("1234", "2   " + item.street!!.streetName + item.isExpand.toString())
                }
            }
        })
    }

    override fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): ItemFeeStandardBinding {
        return ItemFeeStandardBinding.inflate(inflater)
    }

    fun setExpandCallback(callBack: ExpandFeeStandardCallback) {
        this.callback = callBack
    }

    interface ExpandFeeStandardCallback {
        fun expand(feeStandardBean: FeeStandardBean)
    }
}