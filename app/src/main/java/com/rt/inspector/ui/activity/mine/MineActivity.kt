package com.rt.inspector.ui.activity.mine

import android.view.View
import android.view.View.OnClickListener
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.dialog.DialogHelp
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18N
import com.rt.base.ext.startArouter
import com.rt.base.help.ActivityCacheManager
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityMineBinding
import com.rt.inspector.mvvm.viewmodel.MineViewModel
import com.rt.inspector.ui.activity.login.LoginActivity
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.MINE)
class MineActivity : VbBaseActivity<MineViewModel, ActivityMineBinding>(), OnClickListener {

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18N(com.rt.base.R.string.我的)
        binding.layoutToolbar.tvTitle.setTextColor(ContextCompat.getColor(BaseApplication.instance(), com.rt.base.R.color.white))
        GlideUtils.instance?.loadImage(binding.layoutToolbar.ivBack, com.rt.common.R.mipmap.ic_back_white)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvPersonalInfo.setOnClickListener(this)
        binding.tvVersionUpdate.setOnClickListener(this)
        binding.rflLogout.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_personalInfo -> {
                startArouter(ARouterMap.PERSONAL_INFO)
            }

            R.id.tv_versionUpdate -> {

            }

            R.id.rfl_logout -> {
                DialogHelp.Builder().setTitle(i18N(com.rt.base.R.string.确认签退))
                    .setLeftMsg(i18N(com.rt.base.R.string.取消))
                    .setRightMsg(i18N(com.rt.base.R.string.确定)).setCancelable(true)
                    .setOnButtonClickLinsener(object : DialogHelp.OnButtonClickLinsener {
                        override fun onLeftClickLinsener(msg: String) {
                        }

                        override fun onRightClickLinsener(msg: String) {
                            runBlocking {
                                PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.simId, "")
                                PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.phone, "")
                                PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.name, "")
                                PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.loginName, "")
                            }
                            startArouter(ARouterMap.LOGIN)
                            for (i in ActivityCacheManager.instance().getAllActivity()) {
                                if (i !is LoginActivity) {
                                    i.finish()
                                }
                            }
                        }

                    }).build(this@MineActivity).showDailog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityMineBinding.inflate(layoutInflater)
    }

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<MineViewModel> {
        return MineViewModel::class.java
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

}