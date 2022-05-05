package com.zynksoftware.base.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.zynksoftware.base.R

abstract class BaseDialogFragment<B : ViewBinding>(val viewBinder: (LayoutInflater) -> B) :
    DialogFragment() {

    protected var binding: B? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinder(inflater).let {
            binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.onViewCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogStyle)
    }

    abstract fun B.onViewCreated(savedInstanceState: Bundle?)

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}