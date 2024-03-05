package com.rt.inspector.ui.activity.mine

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.blankj.utilcode.util.AppUtils
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.UpdateBean
import com.rt.base.dialog.DialogHelp
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18N
import com.rt.base.ext.startArouter
import com.rt.base.help.ActivityCacheManager
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityMineBinding
import com.rt.inspector.mvvm.viewmodel.MineViewModel
import com.rt.inspector.ui.activity.login.LoginActivity
import com.rt.inspector.util.UpdateUtil
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.MINE)
class MineActivity : VbBaseActivity<MineViewModel, ActivityMineBinding>(), OnClickListener {
    var updateBean: UpdateBean? = null

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
                val param = HashMap<String, Any>()
                val jsonobject = JSONObject()
                jsonobject["version"] = AppUtils.getAppVersionName()
                param["attr"] = jsonobject
                mViewModel.checkUpdate(param)
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
                                PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.name, "")
                                PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.department, "")
                                PreferencesDataStore(BaseApplication.instance()).putString(PreferencesKeys.phone, "")
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

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            checkUpdateLiveData.observe(this@MineActivity) {
                updateBean = it
                if (updateBean?.state == "0") {
                    UpdateUtil.instance?.checkNewVersion(updateBean!!, object : UpdateUtil.UpdateInterface {
                        @RequiresApi(Build.VERSION_CODES.O)
                        override fun requestionPermission() {
                            requestPermissions()
                        }
                    })
                } else {
                    ToastUtil.showMiddleToast("当前已是最新版本")
                }
            }
            errMsg.observe(this@MineActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@MineActivity) {
                dismissProgressDialog()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    fun requestPermissions() {
        var rxPermissions = RxPermissions(this@MineActivity)
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe {
            if (it) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (packageManager.canRequestPackageInstalls()) {
                        UpdateUtil.instance?.downloadFileAndInstall()
                    } else {
                        val uri = Uri.parse("package:${AppUtils.getAppPackageName()}")
                        val intent =
                            Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, uri)
                        requestInstallPackageLauncher.launch(intent)
                    }
                } else {
                    UpdateUtil.instance?.downloadFileAndInstall()
                }
            } else {

            }
        }
    }

    val requestInstallPackageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            UpdateUtil.instance?.downloadFileAndInstall()
        } else {

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