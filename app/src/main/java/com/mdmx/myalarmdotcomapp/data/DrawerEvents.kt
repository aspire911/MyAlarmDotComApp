package com.mdmx.myalarmdotcomapp.data

sealed class DrawerEvents {
    data class OnItemClick(val title: String, val index: Int) : DrawerEvents()
}
