package com.mdmx.myalarmdotcomapp.view.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mdmx.myalarmdotcomapp.R
import com.mdmx.myalarmdotcomapp.data.DrawerEvents
import com.mdmx.myalarmdotcomapp.ui.theme.Orange
import com.mdmx.myalarmdotcomapp.ui.theme.White

@Composable
fun DrawerMenu(title: String, onEvent: (DrawerEvents) -> Unit) {
    val list = stringArrayResource(id = R.array.drawer_list)
    Column {
        Card(
            Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Orange),
            backgroundColor = Orange,
            contentColor = White
        ) {
            Text(
                text = title,
                Modifier
                    .fillMaxSize()
                    .padding(10.dp), textAlign = TextAlign.Left
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(list) { index, title ->
                Row {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_garage_24),
                        contentDescription = stringResource(R.string.garage_doors1)
                    )
                    Text(text = title, modifier = Modifier.clickable {
                        onEvent(DrawerEvents.OnItemClick(title, index))
                    })
                }
            }
        }
    }

}