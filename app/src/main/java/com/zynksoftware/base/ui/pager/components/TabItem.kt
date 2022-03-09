package com.zynksoftware.base.ui.pager.components

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.zynksoftware.base.R

class TabItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val imageView = ImageView(context, attrs, defStyleAttr)

    private var selectedSize = resources.getDimension(R.dimen.tab_item_selected_size).toInt()

    private var coloredZoneHeight = resources.getDimension(R.dimen.bottom_bar_solid_size)
    private var marginBottom = resources.getDimension(R.dimen.tab_item_margin_bottom).toInt()

    init {
        val unselectedSizeWidth = resources.getDimension(R.dimen.tab_item_unselected_size).toInt()
        val unselectedSizeHeight = resources.getDimension(R.dimen.tab_item_unselected_size).toInt()
        val marginTop = (selectedSize - coloredZoneHeight + ((coloredZoneHeight - unselectedSizeHeight) / 2)).toInt()
        setLayoutParamsForImageView(unselectedSizeWidth, unselectedSizeHeight, marginTop, 0)
        imageView.adjustViewBounds = true
        addView(imageView)
    }

    fun setIsTabSelected(isSelected: Boolean, @DrawableRes resId: Int?, position: Int) {
        resId?.let {
            if (isSelected) {
                imageView.setImageDrawable(ContextCompat.getDrawable(context, resId))
                setLayoutParamsForImageView(selectedSize, selectedSize, 0, marginBottom)
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(context, resId))

                // size can be adjusted based on position
                val unselectedSizeWidth = resources.getDimension(R.dimen.tab_item_unselected_width).toInt()
                val unselectedSizeHeight = resources.getDimension(R.dimen.tab_item_unselected_height).toInt()
                val marginTop = (selectedSize - coloredZoneHeight + ((coloredZoneHeight - unselectedSizeHeight) / 2)).toInt()
                setLayoutParamsForImageView(unselectedSizeWidth, unselectedSizeHeight, marginTop, 0)
            }
        }
    }

    private fun setLayoutParamsForImageView(width: Int, height: Int, marginTop: Int, marginBottom: Int) {
        imageView.layoutParams = LayoutParams(width, height).apply {
            this.topMargin = marginTop
            this.bottomMargin = marginBottom
        }
    }
}