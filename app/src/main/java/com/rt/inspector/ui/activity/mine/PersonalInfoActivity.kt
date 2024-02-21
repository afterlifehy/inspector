package com.rt.inspector.ui.activity.mine

import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.Street
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18N
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.realm.RealmUtil
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityPersonalInfoBinding
import com.rt.inspector.mvvm.viewmodel.PersonalInfoViewModel
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.PERSONAL_INFO)
class PersonalInfoActivity : VbBaseActivity<PersonalInfoViewModel, ActivityPersonalInfoBinding>(), OnClickListener {
    var roadList: MutableList<Street> = ArrayList()

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18N(com.rt.base.R.string.个人信息)
        binding.layoutToolbar.tvTitle.setTextColor(ContextCompat.getColor(BaseApplication.instance(), com.rt.base.R.color.white))
        GlideUtils.instance?.loadImage(binding.layoutToolbar.ivBack, com.rt.common.R.mipmap.ic_back_white)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        runBlocking {
            binding.tvAccount.text = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            binding.tvName.text = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.name)
            binding.tvCompany.text = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.department)
        }
        RealmUtil.instance?.findStreetList()?.let { roadList.addAll(it) }
        for (i in roadList) {
            val road = View.inflate(BaseApplication.instance(), R.layout.item_personal_road, null)
            road.findViewById<TextView>(R.id.tv_road).text = i.streetName
            binding.llRoad.addView(road)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityPersonalInfoBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<PersonalInfoViewModel>? {
        return PersonalInfoViewModel::class.java
    }
}