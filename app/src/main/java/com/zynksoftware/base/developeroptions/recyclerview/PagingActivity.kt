package com.zynksoftware.base.developeroptions.recyclerview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zynksoftware.base.databinding.ActivityPagingBinding
import com.zynksoftware.base.common.extensions.observe
import com.zynksoftware.base.developeroptions.recyclerview.adapters.DemoInfiniteAdapter
import com.zynksoftware.base.extensions.observe
import com.zynksoftware.base.ui.common.BaseActivity

class PagingActivity : BaseActivity<ActivityPagingBinding>(ActivityPagingBinding::inflate)  {
    override fun getViewIdToFindNavController(): Int = -1
    override fun getVM() = viewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, PagingActivity::class.java)
            context.startActivity(intent)
        }
    }

    private val viewModel: PagingViewModel by viewModels()

    private val adapter = DemoInfiniteAdapter (
        itemClick = { item, position ->
            viewModel.edit(item, position)
        },
        itemLongClick = { item, position ->
            viewModel.delete(item)
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.recyclerView.setLayoutManager(LinearLayoutManager(this))
        binding.recyclerView.setAdapter(adapter)

        observe(viewModel.listLiveData) {
            adapter.submitData(lifecycle, it)
        }

        viewModel.getDemoList()
    }
}