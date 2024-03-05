package com.rt.inspector.ui.activity.taskreception

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.TaskBean
import com.rt.base.bean.TaskDetailBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18n
import com.rt.base.ext.startArouter
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.util.FileUtil
import com.rt.common.util.GlideUtils
import com.rt.inspector.R
import com.rt.inspector.adapter.TaskReceptionAdapter
import com.rt.inspector.databinding.ActivityTaskReceptionBinding
import com.rt.inspector.mvvm.viewmodel.TaskReceptionViewModel
import kotlinx.coroutines.runBlocking

@Route(path = ARouterMap.TASK_RECEPTION)
class TaskReceptionActivity : VbBaseActivity<TaskReceptionViewModel, ActivityTaskReceptionBinding>(), OnClickListener {
    var taskReceptionAdapter: TaskReceptionAdapter? = null
    var taskReceptionList: MutableList<TaskBean> = ArrayList()
    var loginName = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.任务接收)

        binding.rvTaskReception.setHasFixedSize(true)
        binding.rvTaskReception.layoutManager = LinearLayoutManager(this)
        taskReceptionAdapter = TaskReceptionAdapter(taskReceptionList, this)
        binding.rvTaskReception.adapter = taskReceptionAdapter
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
    }

    override fun initData() {
        showProgressDialog(5000)
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
            val param = HashMap<String, Any>()
            val jsonobject = JSONObject()
            jsonobject["loginName"] = loginName
            param["attr"] = jsonobject
            mViewModel.queryTask(param)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.rll_task -> {
                val taskBean = v.tag as TaskBean
                startArouter(ARouterMap.ABNORMAL_DETAIL, data = Bundle().apply {
                    putString(ARouterMap.TASK_NO, taskBean.taskNo)
                    putString(ARouterMap.TASK_SOURCE, taskBean.taskSource)
                    putString(ARouterMap.ABNORMAL_ID, taskBean.abnormalId)
                })
            }
        }
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            queryTaskLiveData.observe(this@TaskReceptionActivity) {
                dismissProgressDialog()
                taskReceptionList = it.result as MutableList<TaskBean>
                taskReceptionAdapter?.setList(taskReceptionList)
            }
            errMsg.observe(this@TaskReceptionActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@TaskReceptionActivity) {
                dismissProgressDialog()
            }
        }
    }

    override fun getVbBindingView(): ViewBinding {
        return ActivityTaskReceptionBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<TaskReceptionViewModel> {
        return TaskReceptionViewModel::class.java
    }
}