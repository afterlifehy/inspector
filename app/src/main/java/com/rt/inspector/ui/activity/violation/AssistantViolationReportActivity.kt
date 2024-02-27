package com.rt.inspector.ui.activity.violation

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.viewbinding.ViewBinding
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.fastjson.JSONObject
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.FileUtils
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.QueryAssistantNameBean
import com.rt.base.bean.Street
import com.rt.base.ext.i18n
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.realm.RealmUtil
import com.rt.common.util.GlideUtils
import com.rt.common.util.ImageCompressor
import com.rt.common.util.ImageUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityAssistantViolationReportBinding
import com.rt.inspector.dialog.ViolationSelectDialog
import com.rt.inspector.mvvm.viewmodel.AssistantViolationReportViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Route(path = ARouterMap.ASSISTANT_VIOLATION_REPORT)
class AssistantViolationReportActivity : VbBaseActivity<AssistantViolationReportViewModel, ActivityAssistantViolationReportBinding>(),
    OnClickListener {
    var violationSelectDialog: ViolationSelectDialog? = null
    var parkingLotList: MutableList<Street> = ArrayList()
    var nameList: MutableList<QueryAssistantNameBean> = ArrayList()
    var typeList: MutableList<String> = ArrayList()
    var currentParkingLot: Street? = null
    var currentAssistant: QueryAssistantNameBean? = null
    var currentType = ""
    var imageFile: File? = null
    var photoType = 0

    var picBase1 = ""
    var picBase2 = ""
    var picBase3 = ""

    override fun initView() {
        binding.layoutToolbar.tvTitle.text = i18n(com.rt.base.R.string.协管员违规上报)
    }

    override fun initListener() {
        binding.layoutToolbar.flBack.setOnClickListener(this)
        binding.tvParkingLot.setOnClickListener(this)
        binding.tvName.setOnClickListener(this)
        binding.tvType.setOnClickListener(this)
        binding.rivImg1.setOnClickListener(this)
        binding.rivImg2.setOnClickListener(this)
        binding.rivImg3.setOnClickListener(this)
        binding.rflReport.setOnClickListener(this)
    }

    override fun initData() {
        parkingLotList = RealmUtil.instance?.findStreetList() as MutableList<Street>

        typeList.add(i18n(com.rt.base.R.string.工作范围))
        typeList.add(i18n(com.rt.base.R.string.超时未报))
        typeList.add(i18n(com.rt.base.R.string.连续违规))
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.fl_back -> {
                onBackPressedSupport()
            }

            R.id.tv_parkingLot -> {
                violationSelectDialog = ViolationSelectDialog(object : ViolationSelectDialog.ViolationSelectCallBack {
                    override fun parkingLotChoose(parkingLot: Street) {
                        binding.tvParkingLot.text = parkingLot.streetName
                        currentParkingLot = parkingLot
                        binding.tvName.text = ""
                        currentAssistant = null
                    }

                    override fun assistantChoose(assistant: QueryAssistantNameBean) {

                    }

                    override fun typeChoose(type: String) {
                    }
                })
                violationSelectDialog?.setParkingLot(0, parkingLotList, currentParkingLot)
                violationSelectDialog?.show()
                arrowChange(binding.tvParkingLot)
            }

            R.id.tv_name -> {
                if (currentParkingLot != null) {
                    val param = HashMap<String, Any>()
                    val jsonobject = JSONObject()
                    jsonobject["streetNo"] = currentParkingLot?.streetNo
                    param["attr"] = jsonobject
                    mViewModel.queryAssistantName(param)
                }else{
                    ToastUtil.showMiddleToast("请先选择场库")
                }
            }

            R.id.tv_type -> {
                violationSelectDialog = ViolationSelectDialog(object : ViolationSelectDialog.ViolationSelectCallBack {
                    override fun parkingLotChoose(parkingLot: Street) {
                    }

                    override fun assistantChoose(assistant: QueryAssistantNameBean) {
                    }

                    override fun typeChoose(type: String) {
                        binding.tvType.text = type
                        currentType = type
                    }
                })
                violationSelectDialog?.setType(2, typeList, currentType)
                violationSelectDialog?.show()
                arrowChange(binding.tvType)
            }

            R.id.riv_img1 -> {
                photoType = 1
                takePhoto()
            }

            R.id.riv_img2 -> {
                photoType = 2
                takePhoto()
            }

            R.id.riv_img3 -> {
                photoType = 3
                takePhoto()
            }

            R.id.rfl_report -> {
                if (currentParkingLot == null) {
                    ToastUtil.showMiddleToast(i18n(com.rt.base.R.string.请选择场库))
                    return
                }
                if (currentAssistant == null) {
                    ToastUtil.showMiddleToast(i18n(com.rt.base.R.string.请选择姓名))
                    return
                }
                if (currentType.isEmpty()) {
                    ToastUtil.showMiddleToast(i18n(com.rt.base.R.string.请选择类型))
                    return
                }
            }
        }
    }

    fun takePhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photoFile: File? = createImageFile()
        val photoURI: Uri = FileProvider.getUriForFile(
            this,
            "com.rt.inspector.fileprovider",
            photoFile!!
        )
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        takePictureIntent.putExtra("android.intent.extra.quickCapture", true)
        takePictureLauncher.launch(takePictureIntent)
    }

    private fun createImageFile(): File? {
        // 创建图像文件名称
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        imageFile = File(storageDir, "PNG_${timeStamp}_${photoType}.png")
        return imageFile
    }

    val takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            ImageCompressor.compress(this@AssistantViolationReportActivity, imageFile!!, object : ImageCompressor.CompressResult {
                override fun onSuccess(file: File) {
                    val bitmapCompressed = ImageUtil.getCompressedImage(file.absolutePath, 945f, 1240f)
                    FileUtils.delete(imageFile)
                    val bytes = ConvertUtils.bitmap2Bytes(bitmapCompressed)
                    when (photoType) {
                        1 -> {
                            GlideUtils.instance?.loadImageFile(binding.rivImg1, file)
                            picBase1 = EncodeUtils.base64Encode2String(bytes)
                        }

                        2 -> {
                            GlideUtils.instance?.loadImageFile(binding.rivImg2, file)
                            picBase2 = EncodeUtils.base64Encode2String(bytes)
                        }

                        3 -> {
                            GlideUtils.instance?.loadImageFile(binding.rivImg3, file)
                            picBase3 = EncodeUtils.base64Encode2String(bytes)
                        }
                    }
                }

                override fun onError(e: Throwable) {

                }

            })
        }
    }

    fun arrowChange(view: AppCompatTextView) {
        val upDrawable = ContextCompat.getDrawable(BaseApplication.instance(), com.rt.common.R.mipmap.ic_violation_arrow_up)
        upDrawable?.setBounds(0, 0, upDrawable.intrinsicWidth, upDrawable.intrinsicHeight)
        view.setCompoundDrawables(
            null,
            null,
            upDrawable,
            null
        )
        violationSelectDialog?.setOnDismissListener(object : DialogInterface.OnDismissListener {
            override fun onDismiss(dialog: DialogInterface?) {
                val downDrawable =
                    ContextCompat.getDrawable(BaseApplication.instance(), com.rt.common.R.mipmap.ic_violation_arrow_down)
                downDrawable?.setBounds(0, 0, downDrawable.intrinsicWidth, downDrawable.intrinsicHeight)
                view.setCompoundDrawables(
                    null,
                    null,
                    downDrawable,
                    null
                )
            }
        })
    }

    override fun startObserve() {
        super.startObserve()
        mViewModel.apply {
            queryAssistantNameLiveData.observe(this@AssistantViolationReportActivity) {
//                nameList = it.result
                violationSelectDialog = ViolationSelectDialog(object : ViolationSelectDialog.ViolationSelectCallBack {
                    override fun parkingLotChoose(parkingLot: Street) {

                    }

                    override fun assistantChoose(assistant: QueryAssistantNameBean) {
                        binding.tvName.text = assistant.adminName
                        currentAssistant = assistant
                    }

                    override fun typeChoose(type: String) {
                    }
                })
                violationSelectDialog?.setName(1, nameList, currentAssistant)
                violationSelectDialog?.show()
                arrowChange(binding.tvName)
            }
            errMsg.observe(this@AssistantViolationReportActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast(it.msg)
            }
            mException.observe(this@AssistantViolationReportActivity) {
                dismissProgressDialog()
            }
        }
    }


    override fun getVbBindingView(): ViewBinding {
        return ActivityAssistantViolationReportBinding.inflate(layoutInflater)
    }

    override fun onReloadData() {
    }

    override val isFullScreen: Boolean
        get() = true

    override fun marginStatusBarView(): View {
        return binding.layoutToolbar.toolbar
    }

    override fun providerVMClass(): Class<AssistantViolationReportViewModel> {
        return AssistantViolationReportViewModel::class.java
    }
}