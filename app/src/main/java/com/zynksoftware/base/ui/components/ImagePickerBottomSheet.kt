package com.zynksoftware.base.ui.components

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ImagePickerBottomSheetBinding

class ImagePickerBottomSheet : BottomSheetDialogFragment() {

    companion object {
        fun newInstance(listener: ImagePickerBottomSheetListener): ImagePickerBottomSheet {
            val bottomSheetFragment = ImagePickerBottomSheet()
            bottomSheetFragment.setStyle(
                DialogFragment.STYLE_NORMAL,
                R.style.AppBottomSheetDialogTheme
            )
            bottomSheetFragment.listener = listener
            return bottomSheetFragment
        }
    }

    private var binding: ImagePickerBottomSheetBinding? = null

    var listener: ImagePickerBottomSheetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ImagePickerBottomSheetBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireContext().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            binding?.cameraButton?.visibility = View.VISIBLE
        } else {
            binding?.cameraButton?.visibility = View.GONE
        }

        binding?.cameraButton?.setOnClickListener {
            listener?.onCameraClicked()
            dismiss()
        }

        binding?.galleryButton?.setOnClickListener {
            listener?.onGalleryClicked()
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    interface ImagePickerBottomSheetListener {
        fun onGalleryClicked()

        fun onCameraClicked()
    }
}