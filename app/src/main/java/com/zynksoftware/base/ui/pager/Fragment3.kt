package com.zynksoftware.base.ui.pager

import android.os.Bundle
import com.bumptech.glide.Glide
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.FragmentDemoImageBinding
import com.zynksoftware.base.common.extensions.toast
import com.zynksoftware.base.ui.common.BaseFragment
import com.zynksoftware.base.ui.components.ImagePickerBottomSheet
import com.zynksoftware.base.utils.image.ImagePickerUtils

class Fragment3 : BaseFragment<FragmentDemoImageBinding>(FragmentDemoImageBinding::inflate) {

    companion object {
        fun newInstance(): Fragment3 {
            return Fragment3()
        }
    }

    private val imagePickerUtils = ImagePickerUtils(this)

    override fun FragmentDemoImageBinding.onViewCreated(savedInstanceState: Bundle?) {

        uploadPhotoButton.setOnClickListener {
            val picker = ImagePickerBottomSheet.newInstance(object :
                ImagePickerBottomSheet.ImagePickerBottomSheetListener {
                override fun onCameraClicked() {
                    imagePickerUtils.openCamera { file ->
                        Glide.with(requireContext()).load(file).into(imageToUpload)
                    }
                }

                override fun onGalleryClicked() {
                    imagePickerUtils.openGallery { file ->
                        if (file.extension == "svg") {
                            if (context != null) {
                                requireContext().toast(getString(R.string.file_not_supported))
                            }
                        } else {
                            Glide.with(requireContext()).load(file).into(imageToUpload)
                        }
                    }
                }
            })
            picker.show(childFragmentManager, picker.tag)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        imagePickerUtils.destroyListeners()
    }
}