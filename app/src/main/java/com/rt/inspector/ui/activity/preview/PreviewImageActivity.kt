package com.rt.inspector.ui.activity.preview

import android.Manifest
import android.annotation.SuppressLint
import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.util.Constant
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.adapter.SamplePagerAdapter
import com.rt.inspector.databinding.ActivityPreviewImageBinding
import com.rt.inspector.mvvm.viewmodel.PreviewImageViewModel
import com.tbruyelle.rxpermissions3.RxPermissions
import java.util.ArrayList

@Route(path = ARouterMap.PREVIEW_IMAGE)
class PreviewImageActivity : VbBaseActivity<PreviewImageViewModel, ActivityPreviewImageBinding>(), OnClickListener {
    var index = 0
    var imageList: MutableList<String> = ArrayList()
    var imageBsae64List: MutableList<String> = ArrayList()
    var samplePagerAdapter: SamplePagerAdapter? = null
    var imgType = Constant.IMG_STRING

    @SuppressLint("SetTextI18n", "CheckResult")
    override fun initView() {
        imgType = intent.getIntExtra(ARouterMap.IMG_TYPE, 0)
        index = intent.getIntExtra(ARouterMap.IMG_INDEX, 0)
        when (imgType) {
            Constant.IMG_STRING -> {
                imageList = intent.getStringArrayListExtra(ARouterMap.IMG_LIST) as ArrayList<String>
                samplePagerAdapter = SamplePagerAdapter(this, imgType, imageList)
                binding.hvpViewpager.adapter = samplePagerAdapter
                binding.hvpViewpager.currentItem = index
                binding.hvpViewpager.offscreenPageLimit = imageList.size
                binding.rtvIndicator.text = "${(index + 1)}/${imageList.size}"
            }

            Constant.IMG_BYTEARRAY -> {
                imageBsae64List = intent.getStringArrayListExtra(ARouterMap.IMG_LIST)!!
                samplePagerAdapter = SamplePagerAdapter(this, imgType, imageBsae64List)
                binding.hvpViewpager.adapter = samplePagerAdapter
                binding.hvpViewpager.currentItem = index
                binding.hvpViewpager.offscreenPageLimit = imageBsae64List.size
                binding.rtvIndicator.text = "${(index + 1)}/${imageBsae64List.size}"
            }
        }

        binding.hvpViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                when (imgType) {
                    Constant.IMG_STRING -> {
                        binding.rtvIndicator.text = "${(position + 1)}/${imageList.size}"
                    }

                    Constant.IMG_BYTEARRAY -> {
                        binding.rtvIndicator.text = "${(position + 1)}/${imageBsae64List.size}"
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        binding.hvpViewpager.setOnClickListener(this)

        var rxPermissions = RxPermissions(this@PreviewImageActivity)
        rxPermissions.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .subscribe {
                if (it) {
                } else {

                }
            }
    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.pv_img -> {
                onBackPressedSupport()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityPreviewImageBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun providerVMClass(): Class<PreviewImageViewModel> {
        return PreviewImageViewModel::class.java
    }
}