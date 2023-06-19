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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mdmx.myalarmdotcomapp.R
import com.mdmx.myalarmdotcomapp.util.Constant.NEWUSER_URL
import com.mdmx.myalarmdotcomapp.view.Routes
import com.mdmx.myalarmdotcomapp.viewmodel.LoginViewModel


@Composable
fun LoginPage(
    navController: NavHostController,
    viewModel: LoginViewModel
) {

    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var login by rememberSaveable { mutableStateOf("") }
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center) {
        if (isLoading) CircularProgressIndicator()

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

            Text(text = stringResource(R.string.login), fontSize = 20.sp)
            Spacer(Modifier.height(10.dp))
            Text(text = stringResource(R.string.username))

            TextField(
                value = login,
                onValueChange = { login = it },
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, color = Color.Black),
                singleLine = true
            )
            Spacer(Modifier.height(10.dp))
            Text(text = stringResource(R.string.password))

            TextField(
                value = password,
                onValueChange = { password = it },
                Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.Black),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible)
                        ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
                    else ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)
                    val description = if (passwordVisible) stringResource(R.string.hide_password) else stringResource(
                                            R.string.show_password)

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
                    Text(text = stringResource(R.string.keep_me_logged_in))
                }
                Text(text = stringResource(R.string.login_help))
            }

            Button(
                onClick = {


                    viewModel.login(login, password)


                }, Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
                shape = RoundedCornerShape(2.dp)
            ) {
                Text(text = stringResource(R.string.login1), color = Color.White)
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomStart) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = { /*TODO*/
                        },
                        Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Text(text = stringResource(R.string.take_a_tour), color = Color.Black)
                    }
                    Spacer(Modifier.height(5.dp))
                    ClickableText(text = AnnotatedString(stringResource(R.string.not_yet_customer_get_system)),
                        onClick = {
                            uriHandler.openUri(NEWUSER_URL)
                        })
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
    val toast = Toast.makeText(context, "", Toast.LENGTH_LONG)

    LaunchedEffect(Unit) {
        viewModel.result.collect { event ->
            when (event) {

                is LoginViewModel.LoginEvent.Success -> {
                    isLoading = false
                    toast.setText(event.resultText)
                    toast.show()
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) {inclusive = true}
                    }

                }

                is LoginViewModel.LoginEvent.Failure -> {
                    isLoading = false
                    toast.setText(event.errorText)
                    toast.show()
                }

                is LoginViewModel.LoginEvent.Loading -> {
                    isLoading = true
                }

                else -> Unit

            }

        }
    }
}

