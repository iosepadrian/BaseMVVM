package com.zynksoftware.base.ui.pager.components

import androidx.fragment.app.Fragment

data class NavigationModel(
    var fragment: Fragment,
    var unselectedIcon: Int,
    var selectedIcon: Int
)