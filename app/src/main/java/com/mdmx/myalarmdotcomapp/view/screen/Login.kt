package com.mdmx.myalarmdotcomapp.view.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mdmx.myalarmdotcomapp.MyApp.Companion.login
import com.mdmx.myalarmdotcomapp.R
import com.mdmx.myalarmdotcomapp.view.Routes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(navController: NavHostController) {

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var login by rememberSaveable { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var isLogin by rememberSaveable { mutableStateOf(false) }

    Box {
        if (isLogin) {
            Toast.makeText(context, "LOGIN OK", Toast.LENGTH_LONG).show()
            navController.navigate(Routes.Home.route)
        }
        Column(
            Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "logo",
                    Modifier
                        .height(100.dp)
                        .width(200.dp)
                )
            }

            Text(text = "Login", fontSize = 20.sp)
            Spacer(Modifier.height(10.dp))
            Text(text = "Username")

            TextField(
                value = login,
                onValueChange = { login = it },
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
            Text(text = "Password")

            TextField(
                value = password,
                onValueChange = { password = it },
                //label = { Text("Password") },
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black),
                singleLine = true,
                // placeholder = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
                    else ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Spacer(Modifier.height(10.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = false, onCheckedChange = {
                    }, Modifier.padding(0.dp))
                    Text(text = "Keep me logged in")
                }
                Text(text = "Login Help >")
            }

            Button(
                onClick = {
                    if (login.isBlank() || password.isBlank())
                        Toast.makeText(context, "INPUT LOGIN AND PASSWORD", Toast.LENGTH_LONG)
                            .show()
                    else {
                        val toast = Toast.makeText(
                            context,
                            "INCORRECT LOGIN OR PASSWORD",
                            Toast.LENGTH_LONG
                        )
                        Thread {
                            isLogin = login(login, password)
                            if (!isLogin) toast.show()
                        }.start()

                    }
                }, Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text(text = "LOGIN")
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                Column {
                    Button(
                        onClick = { /*TODO*/
                        },
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Text(text = "TAKE A TOUR", color = Color.Black)
                    }
                    Spacer(Modifier.height(5.dp))
                    ClickableText(text = AnnotatedString("Not yet customer? Get system!"),
                        onClick = {
                            uriHandler.openUri("https://www.alarm.com/get_started/finddealer_wizard.aspx")
                        })
                    //https://www.alarm.com/get_started/finddealer_wizard.aspx
                    Spacer(Modifier.height(5.dp))
                    Image(
                        painter = painterResource(id = R.drawable.bottom_logo),
                        contentDescription = "bottom_logo",
                        Modifier
                            .height(30.dp)
                            .fillMaxWidth(),
                        alignment = Alignment.Center
                    )
                }

            }

        }
    }
}


