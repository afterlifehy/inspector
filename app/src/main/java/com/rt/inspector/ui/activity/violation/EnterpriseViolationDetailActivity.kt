package com.rt.inspector.ui.activity.violation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.ArrayMap
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.Constant
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityEnterpriseViolationDetailBinding
import com.rt.inspector.mvvm.viewmodel.EnterpriseViolationDetailViewModel

@Route(path = ARouterMap.ENTERPRISE_VIOLATION_DETAIL)
class EnterpriseViolationDetailActivity : VbBaseActivity<EnterpriseViolationDetailViewModel, ActivityEnterpriseViolationDetailBinding>(),
    OnClickListener {
    var mviolateId = ""
    var reasonMap: MutableMap<String, String> = ArrayMap()
    var stateMap: MutableMap<String, String> = ArrayMap()
    var base64PicList: MutableList<String> = ArrayList()

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.企业违规详情)

        mviolateId = intent.getStringExtra(ARouterMap.ASSISTANT_MVIOLATEID).toString()

        val lp = binding.rivImg1.layoutParams as LinearLayout.LayoutParams
        lp.height = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(78f)) / 3
        binding.rivImg1.layoutParams = lp

        val lp2 = binding.rivImg2.layoutParams as LinearLayout.LayoutParams
        lp2.height = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(78f)) / 3
        binding.rivImg2.layoutParams = lp2

        val lp3 = binding.rivImg3.layoutParams as LinearLayout.LayoutParams
        lp3.height = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(78f)) / 3
        binding.rivImg3.layoutParams = lp3
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.rivImg1.setOnClickListener(this)
        binding.rivImg2.setOnClickListener(this)
        binding.rivImg3.setOnClickListener(this)
    }

    override fun initData() {
        reasonMap["01"] = i18n(com.rt.base.R.string.使用私人二维码收费)

        stateMap["01"] = i18n(com.rt.base.R.string.未处理)
        stateMap["02"] = i18n(com.rt.base.R.string.已解决)
        stateMap["03"] = i18n(com.rt.base.R.string.处理中)

        showProgressDialog(5000)
        val param = HashMap<String, Any>()
        val jsonobject = JSONObject()
        jsonobject["mviolateId"] = mviolateId
        param["attr"] = jsonobject
        mViewModel.enterpriseViolationInfoDetail(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.riv_img1 -> {
                if (base64PicList.size > 0) {
                    startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                        putInt(ARouterMap.IMG_TYPE, Constant.IMG_BYTEARRAY)
                        putInt(ARouterMap.IMG_INDEX, 0)
                        putStringArrayList(ARouterMap.IMG_LIST, base64PicList as ArrayList<String>)
                    })
                }
            }

            R.id.riv_img2 -> {
                if (base64PicList.size > 1) {
                    startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                        putInt(ARouterMap.IMG_TYPE, Constant.IMG_BYTEARRAY)
                        putInt(ARouterMap.IMG_INDEX, 1)
                        putStringArrayList(ARouterMap.IMG_LIST, base64PicList as ArrayList<String>)
                    })
                }
            }

            R.id.riv_img3 -> {
                if (base64PicList.size > 2) {
                    startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                        putInt(ARouterMap.IMG_TYPE, Constant.IMG_BYTEARRAY)
                        putInt(ARouterMap.IMG_INDEX, 2)
                        putStringArrayList(ARouterMap.IMG_LIST, base64PicList as ArrayList<String>)
                    })
                }
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            enterpriseViolationInfoDetailLiveData.observe(this@EnterpriseViolationDetailActivity) {
                dismissProgressDialog()
                val result = it.result
                binding.tvName.text = result.companyName
                binding.tvReason.text = reasonMap[result.type]
                binding.tvTime.text = result.reportTime
                binding.tvStatus.text = stateMap[result.state]
                binding.tvRemark.text = result.comment
                if (result.contentList != null && result.contentList.isNotEmpty()) {
                    GlideUtils.instance?.loadImage(binding.rivImg1, Base64.decode(result.contentList[0], Base64.NO_WRAP))
                    base64PicList.add(result.contentList[0])
                    if (result.contentList.size > 1) {
                        GlideUtils.instance?.loadImage(binding.rivImg2, Base64.decode(result.contentList[1], Base64.NO_WRAP))
                        base64PicList.add(result.contentList[1])
                        if (result.contentList.size > 2) {
                            GlideUtils.instance?.loadImage(binding.rivImg3, Base64.decode(result.contentList[2], Base64.NO_WRAP))
                            base64PicList.add(result.contentList[2])
                        }
                    }
                }
            }
            errMsg.observe(this@EnterpriseViolationDetailActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@EnterpriseViolationDetailActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityEnterpriseViolationDetailBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<EnterpriseViolationDetailViewModel>? {
        return EnterpriseViolationDetailViewModel::class.java
    }
}