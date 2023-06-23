package com.mdmx.myalarmdotcomapp.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mdmx.myalarmdotcomapp.data.DrawerEvents
import com.mdmx.myalarmdotcomapp.util.Constant.EMPTY_STRING
import com.mdmx.myalarmdotcomapp.util.Constant.NO_GARAGE_DOORS
import com.mdmx.myalarmdotcomapp.view.Routes
import com.mdmx.myalarmdotcomapp.view.screen.components.DrawerMenu
import com.mdmx.myalarmdotcomapp.view.screen.components.GarageDoor
import com.mdmx.myalarmdotcomapp.view.screen.components.TopBar
import com.mdmx.myalarmdotcomapp.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(
    navController: NavHostController,
    viewModel: HomeViewModel
) {

    val title = rememberSaveable { mutableStateOf(EMPTY_STRING) }
    val garageState = rememberSaveable { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val refreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.updateSystemData() })

    viewModel.updateSystemData()

    viewModel.logOut.observe(LocalLifecycleOwner.current) {
        if (it) navController.navigate(Routes.Login.route) {
            popUpTo(Routes.Home.route) { inclusive = true }
        }
    }

    viewModel.title.observe(LocalLifecycleOwner.current) {
        title.value = it
    }

    viewModel.garageDoorState.observe(LocalLifecycleOwner.current) { state ->
        if (state != NO_GARAGE_DOORS) garageState.value = true
    }

    Column {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(viewModel, title.value, scaffoldState = scaffoldState) },
            drawerContent = {
                DrawerMenu(title = title.value) { event ->
                    when (event) {
                        is DrawerEvents.OnItemClick -> {
                        }
                    }
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                }
            }
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .pullRefresh(pullRefreshState),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)

                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Text(text = title.value)

                    if (garageState.value) GarageDoor(viewModel)

                    Card(
                        border = BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {

                    }
                }
                if (refreshing)
                    CircularProgressIndicator()
//                PullRefreshIndicator(
//                    refreshing = refreshing,
//                    state = pullRefreshState,
//                )
            }
        }
    }
}