package com.mdmx.myalarmdotcomapp.view.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mdmx.myalarmdotcomapp.data.DrawerEvents
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
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()

    val pullRefreshState =
        rememberPullRefreshState(state.isLoading, { viewModel.updateSystemData() })
    val configuration = LocalConfiguration.current

    if (state.isLogout) navController.navigate(Routes.Login.route) {
        popUpTo(Routes.Home.route) { inclusive = true }
    }

    Column {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(viewModel, state.systemTitle, scaffoldState = scaffoldState) },
            drawerContent = {
                DrawerMenu(title = state.systemTitle) { event ->
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
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .padding(
                            horizontal = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 80.dp else 3.dp,
                            vertical = 3.dp
                        )
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    Text(text = state.systemTitle)

                    if (state.garageState != 0) GarageDoor(state.garageState)

                    Card(
                        border = BorderStroke(1.dp, Color.Gray),
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {

                    }
                }
                if (state.isLoading)
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
//                PullRefreshIndicator(
//                    refreshing = state.isLoading,
//                    state = pullRefreshState,
//                )
            }
        }
    }
}