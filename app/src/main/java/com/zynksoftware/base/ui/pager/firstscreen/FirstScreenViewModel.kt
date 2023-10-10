package com.zynksoftware.base.ui.pager.firstscreen

import com.zynksoftware.base.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FirstScreenViewModel @Inject constructor(): BaseViewModel() {
    private val _state = MutableStateFlow(FirstState())
    val state = _state.asStateFlow()
}

class FirstState