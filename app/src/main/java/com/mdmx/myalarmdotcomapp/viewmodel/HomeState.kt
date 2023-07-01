package com.mdmx.myalarmdotcomapp.viewmodel

import com.mdmx.myalarmdotcomapp.util.Constant.EMPTY_STRING

data class HomeState(
    val isLoading: Boolean = false,
    val isLogout: Boolean = false,
    val systemTitle: String = EMPTY_STRING,
    val garageState: Int = 0
)