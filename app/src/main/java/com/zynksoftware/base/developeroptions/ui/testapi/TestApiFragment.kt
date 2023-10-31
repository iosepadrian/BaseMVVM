package com.zynksoftware.base.developeroptions.ui.testapi

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.zynksoftware.base.common.extensions.observe
import com.zynksoftware.base.databinding.FragmentTestApiBinding
import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.models.RoleRequest
import com.zynksoftware.base.ui.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import android.util.Log

@AndroidEntryPoint
class TestApiFragment : BaseFragment<FragmentTestApiBinding>(FragmentTestApiBinding::inflate) {

    companion object {
        fun newInstance(): TestApiFragment {
            return TestApiFragment()
        }
    }

    private val viewModel: TestApiViewModel by viewModels()

    override fun getVM() = viewModel

    @SuppressLint("CheckResult")
    override fun FragmentTestApiBinding.onViewCreated(savedInstanceState: Bundle?) {

        showUserButtons()

        loginCallButton.setOnClickListener {
            //TODO add edittext validation
            viewModel.login(
                LoginRequestBody(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                    "ANDROID"
                )
            )
        }

        registerCallButton.setOnClickListener {
            //TODO add edittext validation
            viewModel.register(
                RegisterRequestBody(
                    usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                    "ANDROID",
                    false
                )
            )
        }

        forgotPasswordCallButton.setOnClickListener {
            //TODO add edittext validation
            viewModel.forgotPassword(usernameEditText.text.toString())
        }

        logoutCallButton.setOnClickListener {
            viewModel.logout()
        }

        getRolesCallButton.setOnClickListener {
            viewModel.getRoles()
        }

        editRoleCallButton.setOnClickListener {
            viewModel.editRole(RoleRequest(roleIdEditText.text.toString(), emptyList()))
        }

        switchButtons.setOnClickListener {
            switchDevice()
        }

        observe(viewModel.loginResponseLiveData) {
            responseTextView.text = it
        }

        observe(viewModel.registerResponseLiveData) {
            responseTextView.text = it
        }

        observe(viewModel.forgotPasswordResponseLiveData) {
            responseTextView.text = it
        }

        observe(viewModel.logoutResponseLiveData) {
            responseTextView.text = it
        }

        observe(viewModel.errorMessage) {
            responseTextView.text = it
        }

        observe(viewModel.getRolesResponseLiveData) {
            responseTextView.text = it
        }

        observe(viewModel.roleIdLiveData) {
            roleIdEditText.setText(it)
        }
    }

    private fun switchDevice() {
        if (binding?.loginCallButton?.isVisible == true) {
            showRolesButtons()
        } else {
            showUserButtons()
        }
    }

    private fun showRolesButtons() {
        binding?.switchButtons?.text = "User calls"
        binding?.loginCallButton?.isVisible = false
        binding?.registerCallButton?.isVisible = false
        binding?.forgotPasswordCallButton?.isVisible = false
        binding?.logoutCallButton?.isVisible = false
        binding?.usernameEditText?.isVisible = false
        binding?.passwordEditText?.isVisible = false
        binding?.getRolesCallButton?.isVisible = true
        binding?.editRoleCallButton?.isVisible = true
        binding?.roleIdEditText?.isVisible = true
    }

    private fun showUserButtons() {
        binding?.switchButtons?.text = "Roles calls"
        binding?.loginCallButton?.isVisible = true
        binding?.registerCallButton?.isVisible = true
        binding?.forgotPasswordCallButton?.isVisible = true
        binding?.logoutCallButton?.isVisible = true
        binding?.usernameEditText?.isVisible = true
        binding?.passwordEditText?.isVisible = true
        binding?.getRolesCallButton?.isVisible = false
        binding?.editRoleCallButton?.isVisible = false
        binding?.roleIdEditText?.isVisible = false
    }
}