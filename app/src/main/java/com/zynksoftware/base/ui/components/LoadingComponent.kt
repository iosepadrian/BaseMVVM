package com.zynksoftware.base.ui.components

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ComponentLoadingLayoutBinding
import com.zynksoftware.base.utils.network.NetworkConnection

class LoadingComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding = ComponentLoadingLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        hideLoading()
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LoadingComponent, defStyleAttr, 0)

        val color = typedArray.getColor(R.styleable.LoadingComponent_loadingBackground, ContextCompat.getColor(context, R.color.loading_background))

        binding.loadingBackground.setBackgroundColor(color)

        typedArray.recycle()
    }

    private fun hideLoading() {
        this.visibility = View.GONE
    }

    private fun showLoading() {
        this.visibility = View.VISIBLE
    }

    fun setIsLoading(isLoading: Boolean) {
        if (NetworkConnection.isConnected) {
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        } else {
            hideLoading()
        }
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }
}