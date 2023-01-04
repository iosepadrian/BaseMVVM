package com.zynksoftware.base.utils.image

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.allPermanentlyDenied
import com.fondesa.kpermissions.allShouldShowRationale
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.zynksoftware.base.R
import com.zynksoftware.base.common.extensions.toActualFile
import com.zynksoftware.base.common.extensions.toast
import com.zynksoftware.base.ui.common.BaseFragment
import com.zynksoftware.base.utils.ConsumableLiveData
import com.zynksoftware.base.utils.file.FileUtils
import java.io.File

class ImagePickerUtils(private val fragment: BaseFragment<*>) {
    private var cameraTempUri: Uri? = null

    private var galleryListener: ((File) -> Unit)? = null
    private var cameraListener: ((File) -> Unit)? = null
    private var cameraPermissionRequest: PermissionRequest? = null

    val permanentlyDeniedLiveData = ConsumableLiveData<Boolean>(true)
    val shouldShowRationalLiveData = ConsumableLiveData<PermissionRequest>(true)

    private val takeImageFromCameraResult =
        fragment.registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                cameraTempUri?.let { uri ->
                    val file = uri.toActualFile(fragment.requireContext())
                    file?.let {
                        cameraListener?.invoke(file)
                        cameraListener = null
                    }
                }
            }
        }

    private val selectImageFromGalleryResult =
        fragment.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent: Intent? = result.data
                if (intent != null) {
                    intent.data?.let { uri ->
                        uri.let {
                            val file = uri.toActualFile(fragment.requireContext())
                            if (file != null) {
                                galleryListener?.invoke(file)
                                galleryListener = null
                            } else {
                                fragment.showToast(fragment.getString(R.string.something_went_wrong))
                            }
                        }
                    }
                }
            } else {
                fragment.requireContext().toast(fragment.getString(R.string.file_not_selected))
            }

        }

    fun openGallery(listener: (File) -> Unit) {
        this.galleryListener = listener
        val intent =
            Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/jpeg", "image/png", "image/jpg"))
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
            }
        selectImageFromGalleryResult.launch(intent)
    }

    fun openCamera(listener: (File) -> Unit) {
        this.cameraListener = listener

        this.cameraPermissionRequest =
            fragment.permissionsBuilder(Manifest.permission.CAMERA).build()

        cameraPermissionRequest?.addListener { result ->
            when {
                result.allPermanentlyDenied() -> {
                    permanentlyDeniedLiveData.setValue(true)
                }
                result.allShouldShowRationale() -> {
                    cameraPermissionRequest?.let { permissionRequest ->
                        shouldShowRationalLiveData.setValue(permissionRequest)
                    }
                }
                result.allGranted() -> {
                    fragment.lifecycleScope.launchWhenStarted {
                        FileUtils.getTmpFileUri(fragment.requireContext()).let { uri ->
                            cameraTempUri = uri
                            takeImageFromCameraResult.launch(uri)
                        }
                    }
                }
            }
        }
        cameraPermissionRequest?.send()
    }

    fun destroyListeners() {
        this.galleryListener = null
        this.cameraListener = null
        this.cameraTempUri = null
        this.cameraPermissionRequest?.removeAllListeners()
    }
}