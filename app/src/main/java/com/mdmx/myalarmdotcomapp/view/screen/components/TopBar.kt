package com.mdmx.myalarmdotcomapp.view.screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.mdmx.myalarmdotcomapp.R
import com.mdmx.myalarmdotcomapp.ui.theme.Orange
import com.mdmx.myalarmdotcomapp.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun TopBar(viewModel: HomeViewModel, title: String, scaffoldState: ScaffoldState) {
    val coroutine = rememberCoroutineScope()
    TopAppBar(title = {
        Text(
            modifier = Modifier.fillMaxWidth(), text = title, textAlign = TextAlign.Center
        )
    }, backgroundColor = Orange, contentColor = Color.White, navigationIcon = {
        IconButton(onClick = {
            coroutine.launch {
                scaffoldState.drawerState.open()
            }
        }) {
            Icon(
                imageVector = Icons.Filled.Menu, contentDescription = "Menu"
            )
        }
    }, actions = {
        IconButton(onClick = { viewModel.updateSystemData() }) {
            Icon(
                imageVector = Icons.Sharp.Refresh, contentDescription = "Refresh"
            )
        }
        IconButton(onClick = {
            viewModel.logOut()
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_logout_24),
                contentDescription = "LogOut"
            )

        }
    })
}