package com.zynksoftware.base.ui.pager

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.LinearLayoutManager
import com.zynksoftware.base.databinding.FragmentDemo1Binding
import com.zynksoftware.base.models.DemoModel
import com.zynksoftware.base.ui.adapters.DemoAdapter
import com.zynksoftware.base.ui.common.BaseFragment

class Fragment1: BaseFragment<FragmentDemo1Binding>(FragmentDemo1Binding::inflate) {

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }

    override fun FragmentDemo1Binding.onViewCreated(savedInstanceState: Bundle?) {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // this is for adapter example, it may crash if app is closed before handler.postDelayed
        val list = mutableListOf(
            DemoModel("test 1"),
            DemoModel("test 2"),
            DemoModel("test 3"),
            DemoModel("test 4"),
            DemoModel("test 5"),
            DemoModel("test 6")
        )

        val adapter = DemoAdapter(list)
        recyclerView.adapter = adapter

        Handler(Looper.getMainLooper()).postDelayed({
            adapter.updateList(mutableListOf(
                DemoModel("test 1"),
                DemoModel("test 2"),
                DemoModel("test 4"),
                DemoModel("test 5"),
                DemoModel("test 6")
            ))
        }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            adapter.updateList(mutableListOf(
                DemoModel("test 1"),
                DemoModel("test 2"),
                DemoModel("test 4"),
                DemoModel("test 10"),
                DemoModel("test 11")
            ))
        }, 4000)
    }
}