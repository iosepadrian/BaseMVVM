package com.zynksoftware.base.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ComponentButtonBinding

class ButtonComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding = ComponentButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.ButtonComponent, defStyleAttr, 0)

        val color = typedArray.getColor(R.styleable.ButtonComponent_android_textColor, ContextCompat.getColor(context, R.color.button_component_text))
        binding.customButtonTextView.setTextColor(color)
        val text= typedArray.getString(R.styleable.ButtonComponent_android_text)
        binding.customButtonTextView.text = text
        typedArray.recycle()
    }
}