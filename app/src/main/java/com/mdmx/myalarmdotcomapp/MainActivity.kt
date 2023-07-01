package com.mdmx.myalarmdotcomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mdmx.myalarmdotcomapp.view.Routes
import com.mdmx.myalarmdotcomapp.view.screen.Home
import com.mdmx.myalarmdotcomapp.view.screen.LoginPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Routes.Login.route) {

                composable(Routes.Login.route) {
                    LoginPage(navController = navController)
                }

                composable(Routes.Home.route) {
                    Home(navController = navController)
                }

            }
        }
    }
}
