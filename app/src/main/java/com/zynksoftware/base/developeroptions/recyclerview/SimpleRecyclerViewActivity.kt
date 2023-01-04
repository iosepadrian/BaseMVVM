package com.zynksoftware.base.developeroptions.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zynksoftware.base.databinding.ActivitySimpleRecyclerViewBinding
import com.zynksoftware.base.developeroptions.recyclerview.adapters.DemoAdapter
import com.zynksoftware.base.common.extensions.observe
import com.zynksoftware.base.ui.common.BaseActivity

class SimpleRecyclerViewActivity: BaseActivity<ActivitySimpleRecyclerViewBinding>(ActivitySimpleRecyclerViewBinding::inflate) {
    override fun getViewIdToFindNavController(): Int = -1
    override fun getVM() = viewModel

    private val viewModel: SimpleRecyclerViewViewModel by viewModels()

    companion object {
        fun start (context: Context) {
            val intent = Intent(context, SimpleRecyclerViewActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val demoAdapter = DemoAdapter(itemClick = {
            showToast(it.title)
        })

        observe(viewModel.listLiveData) {
            demoAdapter.submitList(it)
            binding.simpleRecyclerView.isRefreshing = false
        }

        binding.simpleRecyclerView.setLayoutManager(LinearLayoutManager(this))
        binding.simpleRecyclerView.setAdapter(demoAdapter, swipeRefreshListener = {
            viewModel.getList(isFromRefresh = true)
        })

        viewModel.getList()
    }
}