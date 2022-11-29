package com.zynksoftware.base.developeroptions.components

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.widget.CompoundButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ComponentDeveloperButtonBinding

class DeveloperButtonComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private val binding =
        ComponentDeveloperButtonBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.DeveloperButtonComponent,
                defStyleAttr,
                0
            )

        val hasSwitch =
            typedArray.getBoolean(R.styleable.DeveloperButtonComponent_hasSwitch, false)
        if (hasSwitch) {
            binding.switchButton.visibility = VISIBLE
            binding.valueTextView.visibility = GONE
        } else {
            binding.switchButton.visibility = GONE
            binding.valueTextView.visibility = VISIBLE
        }

        val titleText =
            typedArray.getString(R.styleable.DeveloperButtonComponent_titleText) ?: ""
        binding.titleTextView.text = titleText

        val subtitleText =
            typedArray.getString(R.styleable.DeveloperButtonComponent_subtitleText) ?: ""
        binding.subtitleTextView.text = subtitleText

        val valueText =
            typedArray.getString(R.styleable.DeveloperButtonComponent_valueText) ?: ""
        binding.valueTextView.text = valueText

        typedArray.recycle()
    }

    fun setSubtitleText(text: String) {
        binding.subtitleTextView.text = text
    }

    fun setValueText(text: String) {
        binding.valueTextView.text = text
    }

    fun getValueText(): String {
        return binding.valueTextView.text.toString()
    }

    override fun dispatchSaveInstanceState(container: SparseArray<Parcelable>) {
        dispatchFreezeSelfOnly(container)
    }

    override fun dispatchRestoreInstanceState(container: SparseArray<Parcelable>) {
        dispatchThawSelfOnly(container)
    }

    fun setSwitchButtonClickListener(listener: ((CompoundButton, Boolean) -> Unit?)) {
        binding.switchButton.setOnCheckedChangeListener { p1, p2 ->
            listener.invoke(p1, p2)
        }
    }

    fun isSwitchChecked(): Boolean {
        return binding.switchButton.isChecked
    }

    fun setSwitchState(state: Boolean) {
        binding.switchButton.isChecked = state
    }

}