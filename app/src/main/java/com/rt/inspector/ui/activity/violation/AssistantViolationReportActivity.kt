package com.rt.inspector.ui.activity.violation

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
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
import com.blankj.utilcode.util.TimeUtils
import com.rt.base.BaseApplication
import com.rt.base.arouter.ARouterMap
import com.rt.base.bean.EnterpriseBean
import com.rt.base.bean.QueryAssistantNameBean
import com.rt.base.bean.Street
import com.rt.base.bean.UploadImageBean
import com.rt.base.ds.PreferencesDataStore
import com.rt.base.ds.PreferencesKeys
import com.rt.base.ext.i18n
import com.rt.base.util.ToastUtil
import com.rt.base.viewbase.VbBaseActivity
import com.rt.common.realm.RealmUtil
import com.rt.common.util.AppUtil
import com.rt.common.util.FileUtil
import com.rt.common.util.GlideUtils
import com.rt.common.util.ImageCompressor
import com.rt.common.util.ImageUtil
import com.rt.inspector.R
import com.rt.inspector.databinding.ActivityAssistantViolationReportBinding
import com.rt.inspector.dialog.ViolationSelectDialog
import com.rt.inspector.mvvm.viewmodel.AssistantViolationReportViewModel
import com.tbruyelle.rxpermissions3.RxPermissions
import kotlinx.coroutines.runBlocking
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Route(path = ARouterMap.ASSISTANT_VIOLATION_REPORT)
class AssistantViolationReportActivity : VbBaseActivity<AssistantViolationReportViewModel, ActivityAssistantViolationReportBinding>(),
    OnClickListener {
    var violationSelectDialog: ViolationSelectDialog? = null
    var parkingLotList: MutableList<Street> = ArrayList()
    var assistantList: MutableList<QueryAssistantNameBean> = ArrayList()
    var typeList: MutableList<String> = ArrayList()
    var currentParkingLot: Street? = null
    var currentAssistant: QueryAssistantNameBean? = null
    var currentType = ""
    var photoType = 0
    var loginName = ""

    var imageBitmap1: Bitmap? = null
    var imageBitmap2: Bitmap? = null
    var imageBitmap3: Bitmap? = null
    var picName1 = ""
    var picName2 = ""
    var picName3 = ""
    var picBase1 = ""
    var picBase2 = ""
    var picBase3 = ""
    var fileList: MutableList<UploadImageBean> = ArrayList()

    var rxPermissions = RxPermissions(this@AssistantViolationReportActivity)

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
        runBlocking {
            loginName = PreferencesDataStore(BaseApplication.instance()).getString(PreferencesKeys.phone)
        }
        typeList.add(i18n(com.rt.base.R.string.超出工作范围))
        typeList.add(i18n(com.rt.base.R.string.异常超时未报))
        typeList.add(i18n(com.rt.base.R.string.使用私人二维码收费))
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

                    override fun enterpriseChoose(enterprise: EnterpriseBean) {

                    }
                })
                violationSelectDialog?.setParkingLot(0, parkingLotList, currentParkingLot)
                violationSelectDialog?.show()
                arrowChange(binding.tvParkingLot)
            }

            R.id.tv_name -> {
                if (currentParkingLot != null) {
                    showProgressDialog(5000)
                    val param = HashMap<String, Any>()
                    val jsonobject = JSONObject()
                    jsonobject["streetNo"] = currentParkingLot?.streetNo
                    param["attr"] = jsonobject
                    mViewModel.queryAssistantName(param)
                } else {
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

                    override fun enterpriseChoose(enterprise: EnterpriseBean) {

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
                if (picName1.isNotEmpty()) {
                    fileList.add(UploadImageBean(picName1, "png", picBase1))
                }
                if (picName2.isNotEmpty()) {
                    fileList.add(UploadImageBean(picName2, "png", picBase2))
                }
                if (picName3.isNotEmpty()) {
                    fileList.add(UploadImageBean(picName3, "png", picBase3))
                }
                showProgressDialog(5000)
                val param = HashMap<String, Any>()
                val jsonobject = JSONObject()
                jsonobject["streetNo"] = currentParkingLot?.streetNo
                jsonobject["loginName"] = loginName
                jsonobject["adminAccount"] = currentAssistant!!.managerAccount
                jsonobject["comment"] = binding.retRemark.text.toString()
                jsonobject["type"] = AppUtil.fillZero((typeList.indexOf(currentType) + 1).toString())
                jsonobject["fileList"] = fileList
                param["attr"] = jsonobject
                mViewModel.assistantViolationReport(param)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun takePhoto() {
        rxPermissions.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ).subscribe {
            if (it) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val photoFile: File? = createImageFile()
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "com.rt.inspector.fileprovider",
                    photoFile!!
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                takePictureIntent.putExtra("android.intent.extra.quickCapture", true)
                when (photoType) {
                    1 -> {
                        takePictureLauncher1.launch(takePictureIntent)
                    }

                    2 -> {
                        takePictureLauncher2.launch(takePictureIntent)
                    }

                    3 -> {
                        takePictureLauncher3.launch(takePictureIntent)
                    }
                }
            }
        }
    }

    var imageFile1: File? = null
    var imageFile2: File? = null
    var imageFile3: File? = null
    private fun createImageFile(): File? {
        // 创建图像文件名称
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        if (photoType == 1) {
            imageFile1 = File(storageDir, "PNG_${generateRandomFileName()}_${photoType}.png")
            return imageFile1
        } else if (photoType == 2) {
            imageFile2 = File(storageDir, "PNG_${generateRandomFileName()}_${photoType}.png")
            return imageFile2
        } else if (photoType == 3) {
            imageFile3 = File(storageDir, "PNG_${generateRandomFileName()}_${photoType}.png")
            return imageFile3
        } else {
            return null
        }
    }

    val takePictureLauncher1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            ImageCompressor.compress(this@AssistantViolationReportActivity, imageFile1!!, object : ImageCompressor.CompressResult {
                override fun onSuccess(file: File) {
                    imageBitmap1 = ImageUtil.getCompressedImage(file.absolutePath, 945f, 1140f)
                    FileUtils.delete(imageFile1)
                    GlideUtils.instance?.loadImageFile(binding.rivImg1, file)
                    val savedFile = FileUtil.FileSaveToInside("${generateRandomFileName()}_1.png", imageBitmap1!!)
                    picName1 = savedFile.name
                    picBase1 = FileUtil.fileToBase64(savedFile).toString()
                }

                override fun onError(e: Throwable) {

                }

            })
        }
    }

    val takePictureLauncher2 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            ImageCompressor.compress(this@AssistantViolationReportActivity, imageFile2!!, object : ImageCompressor.CompressResult {
                override fun onSuccess(file: File) {
                    imageBitmap2 = ImageUtil.getCompressedImage(file.absolutePath, 945f, 1140f)
                    FileUtils.delete(imageFile2)
                    GlideUtils.instance?.loadImageFile(binding.rivImg2, file)
                    val savedFile = FileUtil.FileSaveToInside("${generateRandomFileName()}_2.png", imageBitmap2!!)
                    picName2 = savedFile.name
                    picBase2 = FileUtil.fileToBase64(savedFile).toString()
                }

                override fun onError(e: Throwable) {

                }

            })
        }
    }

    val takePictureLauncher3 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            ImageCompressor.compress(this@AssistantViolationReportActivity, imageFile3!!, object : ImageCompressor.CompressResult {
                override fun onSuccess(file: File) {
                    imageBitmap3 = ImageUtil.getCompressedImage(file.absolutePath, 945f, 1140f)
                    FileUtils.delete(imageFile3)
                    GlideUtils.instance?.loadImageFile(binding.rivImg3, file)
                    val savedFile = FileUtil.FileSaveToInside("${generateRandomFileName()}_3.png", imageBitmap3!!)
                    picName3 = savedFile.name
                    picBase3 = FileUtil.fileToBase64(savedFile).toString()
                }

                override fun onError(e: Throwable) {

                }

            })
        }
    }

    fun generateRandomFileName(): String {
        val timeStamp = TimeUtils.millis2String(System.currentTimeMillis(), "yyyy-MM-ddHH:mm:ss")
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        val randomPart = (1..4)
            .map { chars.random() }
            .joinToString("")
        return "$timeStamp$randomPart"
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
                dismissProgressDialog()
                assistantList = it.result as MutableList<QueryAssistantNameBean>
                violationSelectDialog = ViolationSelectDialog(object : ViolationSelectDialog.ViolationSelectCallBack {
                    override fun parkingLotChoose(parkingLot: Street) {

                    }

                    override fun assistantChoose(assistant: QueryAssistantNameBean) {
                        binding.tvName.text = assistant.adminName
                        currentAssistant = assistant
                    }

                    override fun typeChoose(type: String) {
                    }

                    override fun enterpriseChoose(enterprise: EnterpriseBean) {

                    }
                })
                violationSelectDialog?.setAssistant(1, assistantList, currentAssistant)
                violationSelectDialog?.show()
                arrowChange(binding.tvName)
            }
            assistantViolationReportLiveData.observe(this@AssistantViolationReportActivity) {
                dismissProgressDialog()
                ToastUtil.showMiddleToast("上报成功")
                onBackPressedSupport()
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

    override fun onDestroy() {
        super.onDestroy()
        imageBitmap1?.recycle()
        imageBitmap1 = null
        imageBitmap2?.recycle()
        imageBitmap1 = null
        imageBitmap3?.recycle()
        imageBitmap1 = null
        System.gc()
    }
}