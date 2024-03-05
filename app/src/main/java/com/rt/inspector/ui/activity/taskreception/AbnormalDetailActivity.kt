package com.rt.inspector.ui.activity.taskreception

import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.TaskBean
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.Constant
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityAbnormalDetailBinding
import com.rt.inspector.mvvm.viewmodel.AbnormalDetailViewModel

@Route(path = ARouterMap.ABNORMAL_DETAIL)
class AbnormalDetailActivity : VbBaseActivity<AbnormalDetailViewModel, ActivityAbnormalDetailBinding>(), OnClickListener {
    var picList: MutableList<String> = ArrayList()
    var taskNo = ""
    var taskSource = ""
    var abnormalId = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.泊位异常详情)

        taskNo = intent.getStringExtra(ARouterMap.TASK_NO).toString()
        taskSource = intent.getStringExtra(ARouterMap.TASK_SOURCE).toString()
        abnormalId = intent.getStringExtra(ARouterMap.ABNORMAL_ID).toString()

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
        val param = HashMap<String, Any>()
        val jsonobject = JSONObject()
        jsonobject["taskNo"] = taskNo
        jsonobject["taskSource"] = taskSource
        jsonobject["abnormalId"] = abnormalId
        param["attr"] = jsonobject
        mViewModel.queryTaskDetail(param)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.riv_img1 -> {
                startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                    putInt(ARouterMap.IMG_TYPE, Constant.IMG_STRING)
                    putInt(ARouterMap.IMG_INDEX, 0)
                    putStringArrayList(ARouterMap.IMG_LIST, picList as ArrayList<String>)
                })
            }

            R.id.riv_img2 -> {
                startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                    putInt(ARouterMap.IMG_TYPE, Constant.IMG_STRING)
                    putInt(ARouterMap.IMG_INDEX, 1)
                    putStringArrayList(ARouterMap.IMG_LIST, picList as ArrayList<String>)
                })
            }

            R.id.riv_img3 -> {
                startArouter(ARouterMap.PREVIEW_IMAGE, data = Bundle().apply {
                    putInt(ARouterMap.IMG_TYPE, Constant.IMG_STRING)
                    putInt(ARouterMap.IMG_INDEX, 2)
                    putStringArrayList(ARouterMap.IMG_LIST, picList as ArrayList<String>)
                })
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            queryTaskDetailLiveData.observe(this@AbnormalDetailActivity) {
                dismissProgressDialog()
                binding.tvAbnormalNum.text = it.taskNo
                binding.tvReportPerson.text = it.name
                binding.tvTelephone.text = it.phone
                binding.tvAbnormalTime.text = it.reportTime
                binding.tvAbnormalDesc.text = it.abnormalDesc
                binding.tvProblemAnalysis.text = it.remark
                if (it.abnormalPhotoPath != null && it.abnormalPhotoPath.isNotEmpty()) {
                    GlideUtils.instance?.loadImage(binding.rivImg1, it.abnormalPhotoPath[0])
                    picList.add(it.abnormalPhotoPath[0])
                    if (it.abnormalPhotoPath.size > 1) {
                        GlideUtils.instance?.loadImage(binding.rivImg2, it.abnormalPhotoPath[1])
                        picList.add(it.abnormalPhotoPath[1])
                        if (it.abnormalPhotoPath.size > 2) {
                            GlideUtils.instance?.loadImage(binding.rivImg3, it.abnormalPhotoPath[2])
                            picList.add(it.abnormalPhotoPath[2])
                        }
                    }
                }
            }
            errMsg.observe(this@AbnormalDetailActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@AbnormalDetailActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityAbnormalDetailBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<AbnormalDetailViewModel> {
        return AbnormalDetailViewModel::class.java
    }
}