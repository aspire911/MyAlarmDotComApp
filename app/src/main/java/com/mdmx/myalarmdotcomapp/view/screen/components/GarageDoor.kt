package com.mdmx.myalarmdotcomapp.view.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mdmx.myalarmdotcomapp.MyApp.Companion.getGarageDoorState
import com.mdmx.myalarmdotcomapp.R


@Composable
fun GarageDoor(garageDoorId: String) {
    var imgId by rememberSaveable { mutableIntStateOf(R.drawable.close_garage) }
    Thread() {
        val state = getGarageDoorState(garageDoorId)
        imgId = if (state == 3 || state == 1) R.drawable.open_garage else R.drawable.close_garage
    }.start()

    Card(
        border = BorderStroke(1.dp, Color.Gray),
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "GARAGE DOORS")
                Text(text = ">")
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = imgId),
                    contentDescription = "logo",
                    Modifier
                        .height(50.dp)
                        .width(50.dp)

                )
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(text = "Garage Door")
                    if (imgId == R.drawable.open_garage)
                        Text(text = "OPEN", color = Color.Green)
                    else
                        Text(text = "CLOSE", color = Color.Red)
                }


            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GarageDoor("")
}


