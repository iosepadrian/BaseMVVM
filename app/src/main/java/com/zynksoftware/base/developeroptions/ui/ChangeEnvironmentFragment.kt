package com.zynksoftware.base.developeroptions.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.DialogFragment
import com.google.android.material.switchmaterial.SwitchMaterial
import com.zynksoftware.base.BuildConfig
import com.zynksoftware.base.databinding.FragmentChangeEnvironmentBinding
import com.zynksoftware.base.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChangeEnvironmentFragment : DialogFragment() {

    companion object {
        fun newInstance(): ChangeEnvironmentFragment {
            return ChangeEnvironmentFragment()
        }

        const val DEFAULT_ENVIRONMENT = "Default"
        const val STAGING_ENVIRONMENT = "Staging"
        const val PRODUCTION_ENVIRONMENT = "Production"
    }

    private var listener: ChangeEnvironmentListener? = null

    interface ChangeEnvironmentListener {
        fun getEnvironment(): String?

        fun getServerURL(): String?

        fun setEnvironment(environment: String)

        fun setServerURL(url: String)

        suspend fun logout()
    }

    fun setChangeEnvironmentListener(listener: ChangeEnvironmentListener) {
        this.listener = listener
    }

    private val languageSwitches: ArrayList<SwitchMaterial> = ArrayList()

    private var binding: FragmentChangeEnvironmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeEnvironmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addSwitchesToList()
        initSwitches()
        setSwitchesListeners()
    }

    private fun initSwitches() {
        when (listener?.getEnvironment()) {
            DEFAULT_ENVIRONMENT -> binding?.defaultEnvironmentSwitch?.isChecked = true
            STAGING_ENVIRONMENT -> binding?.stagingEnvironmentSwitch?.isChecked = true
            PRODUCTION_ENVIRONMENT -> binding?.productionEnvironmentSwitch?.isChecked = true
            else -> {}
        }
    }

    private fun setSwitchesListeners() {
        binding?.defaultEnvironmentSwitch?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                clickedSwitch(0)
                changeEnvironment(DEFAULT_ENVIRONMENT, BuildConfig.SERVER_URL)
            }
        }
        binding?.productionEnvironmentSwitch?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                clickedSwitch(1)
                changeEnvironment(PRODUCTION_ENVIRONMENT, BuildConfig.PRODUCTION_SERVER_URL)
            }
        }
        binding?.stagingEnvironmentSwitch?.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                clickedSwitch(2)
                changeEnvironment(STAGING_ENVIRONMENT, BuildConfig.STAGING_SERVER_URL)
            }
        }
    }

    private fun changeEnvironment(environment: String, serverURL: String) {
        CoroutineScope(Dispatchers.IO).launch {
            listener?.logout()
            listener?.setEnvironment(environment)
            listener?.setServerURL(serverURL)
            triggerRestart(requireActivity())
        }
    }

    private fun triggerRestart(context: Activity) {
        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

        context.finishAffinity()

        Runtime.getRuntime().exit(0)
    }

    override fun onStart() {
        super.onStart()
        setDefaultDialog()
    }

    private fun setDefaultDialog() {
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun clickedSwitch(id: Int) {
        val languageSwitch: SwitchMaterial = languageSwitches[id]
        if (languageSwitch.isChecked) {
            for (i in languageSwitches.indices) {
                if (i != id) {
                    languageSwitches[i].isChecked = false
                }
            }
        }
    }

    private fun addSwitchesToList() {
        addSwitchToList(binding?.defaultEnvironmentSwitch)
        addSwitchToList(binding?.productionEnvironmentSwitch)
        addSwitchToList(binding?.stagingEnvironmentSwitch)
    }

    private fun addSwitchToList(switch: SwitchMaterial?) {
        if (switch != null) {
            languageSwitches.add(switch)
        }
    }

}