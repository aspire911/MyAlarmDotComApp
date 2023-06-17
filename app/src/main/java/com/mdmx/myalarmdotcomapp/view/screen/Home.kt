package com.mdmx.myalarmdotcomapp.view.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mdmx.myalarmdotcomapp.model.MyApp.Companion.getSystemData
import com.mdmx.myalarmdotcomapp.data.DrawerEvents
import com.mdmx.myalarmdotcomapp.data.systemdata.SystemData
import com.mdmx.myalarmdotcomapp.view.screen.components.DrawerMenu
import com.mdmx.myalarmdotcomapp.view.screen.components.GarageDoor
import com.mdmx.myalarmdotcomapp.view.screen.components.TopBar
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavHostController) {

    var text by rememberSaveable { mutableStateOf("") }
    var garageDoorId by rememberSaveable { mutableStateOf("") }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var systemData: SystemData?

    Thread() {
        systemData = getSystemData()
        text = systemData!!.data.attributes.description
        garageDoorId = systemData!!.data.relationships.garageDoors.data[0].id
    }.start()

    Column {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = { TopBar(text, scaffoldState = scaffoldState) },
            drawerContent = {
                DrawerMenu(title = text) { event ->
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
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = text)

                if (garageDoorId != "") {
                    GarageDoor(garageDoorId)
                }

                Card(
                    border = BorderStroke(1.dp, Color.Gray),
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    Text(text = text)
                }
            }
        }


    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    Home()
//}
//
