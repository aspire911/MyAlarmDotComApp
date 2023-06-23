package com.mdmx.myalarmdotcomapp.view.screen

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdmx.myalarmdotcomapp.view.Routes
import com.mdmx.myalarmdotcomapp.viewmodel.HomeViewModel
import com.mdmx.myalarmdotcomapp.viewmodel.LoginViewModel


@Composable
fun ScreenMain(loginViewModel: LoginViewModel, homeViewModel: HomeViewModel) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Login.route) {

        composable(Routes.Login.route) {
            LoginPage(navController = navController, loginViewModel)
        }

        composable(Routes.Home.route) {
            Home(navController = navController, homeViewModel)
        }

    }
}