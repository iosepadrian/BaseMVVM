package com.zynksoftware.base.ui.components

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ComponentToolbarBinding

class BaseToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ComponentToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.BaseToolbar, defStyleAttr, 0)

        when (typedArray.getInteger(R.styleable.BaseToolbar_backButtonVisibility, 0)) {
            0 -> setBackButtonVisibility(View.VISIBLE)
            1 -> setBackButtonVisibility(View.INVISIBLE)
            2 -> setBackButtonVisibility(View.GONE)
        }
        typedArray.recycle()
    }

    fun setBackButtonClickListener(listener: () -> Unit) {
        binding.backButton.setOnClickListener {
            listener.invoke()
        }
    }

    fun setBackButtonVisibility(visibility: Int) {
        binding.backButton.visibility = visibility
    }

    fun setTitleVisibility(visibility: Int) {
        binding.toolbarLogo.visibility = visibility
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

}