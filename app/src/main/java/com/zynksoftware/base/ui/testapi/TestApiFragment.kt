package com.zynksoftware.base.ui.testapi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.zynksoftware.base.common.extensions.observe
import com.zynksoftware.base.databinding.FragmentTestApiBinding
import com.zynksoftware.base.developeroptions.recyclerview.SimpleRecyclerViewViewModel
import com.zynksoftware.base.models.LoginRequestBody
import com.zynksoftware.base.models.RegisterRequestBody
import com.zynksoftware.base.ui.common.BaseFragment
import com.zynksoftware.base.ui.common.BaseViewModel
import com.zynksoftware.base.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestApiFragment : BaseFragment<FragmentTestApiBinding>(FragmentTestApiBinding::inflate) {

    companion object {
        fun newInstance(): TestApiFragment {
            return TestApiFragment()
        }
    }

    private val viewModel: TestApiViewModel by viewModels()

    override fun getVM() = viewModel

    override fun FragmentTestApiBinding.onViewCreated(savedInstanceState: Bundle?) {

        loginCallButton.setOnClickListener {
            //TODO add edittext validation
            viewModel.login(LoginRequestBody(usernameEditText.text.toString(), passwordEditText.text.toString(), "Android"))
        }

        registerCallButton.setOnClickListener {
            //TODO add edittext validation
            //TODO not tested yet
            viewModel.register(RegisterRequestBody(usernameEditText.text.toString(), passwordEditText.text.toString(),"Android", false))
        }

        logoutCallButton.setOnClickListener {
            showToast("Not implemented yet")
        }

        observe(viewModel.loginResponseLiveData) {
            responseTextView.text = it
        }

        observe(viewModel.registerResponseLiveData) {
            responseTextView.text = it
        }
    }
}