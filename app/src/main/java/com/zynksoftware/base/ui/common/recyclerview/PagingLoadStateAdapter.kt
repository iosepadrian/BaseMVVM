package com.zynksoftware.base.ui.common.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ViewHolderItemNetworkStateBinding

class PagingLoadStateAdapter(private val retryCallback: () -> Unit): LoadStateAdapter<PagingLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder(
            ViewHolderItemNetworkStateBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.view_holder_item_network_state, parent, false)
            )
        ) {
            retryCallback()
        }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class NetworkStateItemViewHolder(
        private val binding: ViewHolderItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.holderRetryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                holderProgressBar.isVisible = loadState is LoadState.Loading
                holderRetryButton.isVisible = loadState is LoadState.Error
                holderErrorMessageTextView.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                holderErrorMessageTextView.text = (loadState as? LoadState.Error)?.error?.message
            }
        }
    }
}