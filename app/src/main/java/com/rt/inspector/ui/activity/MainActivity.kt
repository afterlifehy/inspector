package com.rt.inspector.ui.activity

import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.i18N
import com.rt.base.help.ActivityCacheManager
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.AppUtil
import com.rt.inspector.databinding.ActivityMainBinding
import com.rt.inspector.mvvm.viewmodel.MainViewModel

@Route(path = ARouterMap.MAIN)
class MainActivity: VbBaseActivity<MainViewModel, ActivityMainBinding>() {

    override fun initView() {

    }

    override fun initListener() {
    }

    override fun initData() {
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = false

    override fun onBackPressedSupport() {
        if (AppUtil.isFastClick(1000)) {
            ActivityCacheManager.instance().getAllActivity().forEach {
                if (!it.isFinishing) {
                    it.finish()
                }
            }
        } else {
            ToastUtil.showMiddleToast(i18N(com.rt.base.R.string.再按一次退出程序))
        }
    }

}