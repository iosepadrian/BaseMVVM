package com.zynksoftware.base.ui.pager.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zynksoftware.base.R
import com.zynksoftware.base.databinding.ComponentNavigationBinding
import com.zynksoftware.base.utils.ConsumableLiveData

class NavigationComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ComponentNavigationBinding.inflate(LayoutInflater.from(context), this, true)

    private var listener: OnPageChangedListener? = null
    private lateinit var navigationModel: ArrayList<NavigationModel>

    fun initialize(
        viewPager: ViewPager2,
        fragmentActivity: FragmentActivity,
        navigationModel: ArrayList<NavigationModel>,
        tabPositionLiveData: ConsumableLiveData<Int>
    ) {
        this.navigationModel = navigationModel
        setOnDragListener(null)
        viewPager.offscreenPageLimit = 1
        viewPager.isUserInputEnabled = false

        val fragments = navigationModel.map { it.fragment }
        viewPager.adapter = NavigationAdapter(fragmentActivity, fragments)

        TabLayoutMediator(binding.tabLayout, viewPager) { tab, _ ->
            tab.setCustomView(R.layout.icon_tab_layout_dashboard)
        }.attach()

        navigationModel.forEachIndexed { index, model ->
            unselectTab(index, model.unselectedIcon)
        }

        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                selectTab(position, navigationModel[position].selectedIcon)
                tabPositionLiveData.setValue(tab.position)
                listener?.onTabSelected(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val position = tab!!.position
                unselectTab(position, navigationModel[position].unselectedIcon)
            }
        })

        val position = tabPositionLiveData.value!!
        viewPager.currentItem = position
        selectTab(position, navigationModel[position].selectedIcon)

        binding.tabLayout.setSelectedTabIndicator(null)
    }

    fun selectTabInTabLayout(position: Int, viewPager: ViewPager2, tabPositionLiveData: ConsumableLiveData<Int>) {
        binding.tabLayout.setScrollPosition(position, 0f, true)
        selectTab(position, navigationModel[position].selectedIcon)
        viewPager.post{
            viewPager.setCurrentItem(position, false)
            tabPositionLiveData.setValue(position)
        }
    }

    private fun unselectTab(position: Int, imageDrawable: Int) {
        getTabItemViewAt(position)?.setIsTabSelected(false, imageDrawable, position)
    }

    fun selectTab(position: Int, imageDrawable: Int) {
        getTabItemViewAt(position)?.setIsTabSelected(true, imageDrawable, position)
    }
    
    private fun getTabItemViewAt(position: Int): TabItem? {
        return binding.tabLayout.getTabAt(position)?.customView as? TabItem
    }

    fun setTabSelectedListener(onPageChangedListener: OnPageChangedListener) {
        this.listener = onPageChangedListener
    }

}

interface OnPageChangedListener {
    fun onTabSelected(position: Int)
}