package com.rt.inspector.ui.activity.attendance

import android.view.View
import android.view.View.OnClickListener
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.TimeUtils
import com.rt.base.arouter.ARouterMap
import com.rt.base.ext.gone
import com.rt.base.ext.hide
import com.rt.base.ext.i18n
import com.rt.base.ext.show
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityWorkAttendanceBinding
import com.rt.inspector.mvvm.viewmodel.WorkAttendanceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Route(path = ARouterMap.WORK_ATTENDANCE)
class WorkAttendanceActivity : VbBaseActivity<WorkAttendanceViewModel, ActivityWorkAttendanceBinding>(), OnClickListener {
    private var job: Job? = null
    var type = 0 //0未签到 1未签退 2已签退

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.考勤)
        job = GlobalScope.launch(Dispatchers.IO) {
            while (true) {
                val time = TimeUtils.millis2String(System.currentTimeMillis(), "HH:mm:ss")
                withContext(Dispatchers.Main) {
                    binding.rtvHour1.text = time.split(":")[0][0].toString()
                    binding.rtvHour2.text = time.split(":")[0][1].toString()
                    binding.rtvMinute1.text = time.split(":")[1][0].toString()
                    binding.rtvMinute2.text = time.split(":")[1][1].toString()
                    binding.rtvSec1.text = time.split(":")[2][0].toString()
                    binding.rtvSec2.text = time.split(":")[2][1].toString()
                }
                delay(1000)
            }
        }

        when (type) {
            0 -> {
                binding.tvLogInOut.text = i18n(com.rt.base.R.string.上班打卡)
            }

            1 -> {
                binding.tvLogInOut.text = i18n(com.rt.base.R.string.下班打卡)
            }

            2 -> {
                binding.tvLogInOut.hide()
            }
        }
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvLogInOut.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_logInOut -> {
                when (type) {
                    0 -> {
                        type = 1
                        binding.tvOnWorkTime.show()
                        binding.rtvNoSignIn.gone()
                        binding.tvLogInOut.text = i18n(com.rt.base.R.string.下班打卡)
                        binding.tvOnWorkTime.text = TimeUtils.millis2String(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")
                    }

                    1 -> {
                        type = 2
                        binding.tvOffWorkTime.show()
                        binding.rtvNoSignOut.gone()
                        binding.tvLogInOut.hide()
                        binding.tvOffWorkTime.text = TimeUtils.millis2String(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")
                    }

                }
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            errMsg.observe(this@WorkAttendanceActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@WorkAttendanceActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        GlobalScope.launch(Dispatchers.IO) {
            job?.cancelAndJoin()
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityWorkAttendanceBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<WorkAttendanceViewModel> {
        return WorkAttendanceViewModel::class.java
    }
}