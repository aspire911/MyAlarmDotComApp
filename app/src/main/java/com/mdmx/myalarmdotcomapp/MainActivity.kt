package com.mdmx.myalarmdotcomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.mdmx.myalarmdotcomapp.util.Logger
import com.mdmx.myalarmdotcomapp.view.screen.ScreenMain
import com.mdmx.myalarmdotcomapp.viewmodel.HomeViewModel
import com.mdmx.myalarmdotcomapp.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ScreenMain(loginViewModel, homeViewModel)
        }
    }
}
