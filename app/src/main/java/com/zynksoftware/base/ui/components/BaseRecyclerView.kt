package com.zynksoftware.base.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.paging.LoadState
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zynksoftware.base.databinding.ComponentRecyclerViewBinding
import com.zynksoftware.base.ui.common.recyclerview.PagingLoadStateAdapter

class BaseRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : SwipeRefreshLayout(context, attrs) {

    private val binding = ComponentRecyclerViewBinding.inflate(LayoutInflater.from(context), this)

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        binding.componentRecyclerView.layoutManager = layoutManager
    }

//      TODO: other param for R.layout.footer, default existing one.
    fun setAdapter(adapter: PagingDataAdapter<*, *>) {
        val footerLoadStateAdapter = PagingLoadStateAdapter { adapter.retry() }
        binding.componentRecyclerView.adapter = adapter.withLoadStateFooter(footerLoadStateAdapter)
        this.setOnRefreshListener {
            adapter.refresh()
        }

        adapter.addLoadStateListener { loadStates ->
            isRefreshing = loadStates.refresh is LoadState.Loading
        }
    }

    fun setAdapter(adapter: ListAdapter<*, *>, swipeRefreshListener: () -> Unit) {
        binding.componentRecyclerView.adapter = adapter
        this.setOnRefreshListener {
            swipeRefreshListener.invoke()
        }
    }
}