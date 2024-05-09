package com.example.braguia.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.braguia.R
import com.example.braguia.ui.components.AlertConfirmDialog
import com.example.braguia.ui.components.AlertDialogTemplate
import com.example.braguia.viewModel.UserLoginState


@Composable
fun LoginScreen(
    appName: String,
    login: (String, String) -> Unit,
    onDismiss: () -> Unit,
    userLoginState: UserLoginState,
    grantAccess: () -> Unit,
    googleMapsAskAgain: Boolean,
    dontAskAgain: (Boolean) -> Unit,
    alreadyAskedtoggle: () -> Unit,
    alreadyAsked: Boolean,
) {

    var showing by remember { mutableStateOf(true) }
    var checked by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        when (userLoginState) {
            UserLoginState.Loading -> DisplayLoading(appName)
            UserLoginState.LoggedIn -> {
                grantAccess()
            }

            else -> {
                if (showing && googleMapsAskAgain && !alreadyAsked) {
                    AlertDialogTemplate(
                        onDismiss = { showing = false;alreadyAskedtoggle();dontAskAgain(!checked) },
                        dialogTitle = stringResource(id = R.string.warningGoogleMaps),
                        dialogText = stringResource(id = R.string.warningGoogleMapsText),
                        confirmButton = {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(R.string.dontAskAgainText))
                                Checkbox(checked = checked, onCheckedChange = { checked = it })
                            }
                        }
                    )
                }
                LoginPrompt(appName, login, onDismiss, userLoginState)
            }
        }
    }
}

@Composable
fun LoginPrompt(
    appName: String,
    login: (String, String) -> Unit,
    onDismiss: () -> Unit,
    userLoginState: UserLoginState
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column {
        Text(
            text = appName,
            style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(stringResource(R.string.EnterUsername)) },
            isError = userLoginState == UserLoginState.CredentialsWrong,
            singleLine = true,
            enabled = userLoginState != UserLoginState.Loading,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.EnterPassword)) },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            enabled = userLoginState != UserLoginState.Loading,
            isError = userLoginState == UserLoginState.CredentialsWrong,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            onClick = {
                login(username, password)
                username = ""
                password = ""
            },
            enabled = userLoginState != UserLoginState.Loading,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            if (userLoginState == UserLoginState.Loading) {
                Image(
                    painter = painterResource(id = R.drawable.loading_img),
                    contentDescription = "LoadingLogin"
                )
            } else {
                Text(stringResource(id = R.string.Login))
            }
        }
        if (userLoginState == UserLoginState.Error) {
            AlertDialogTemplate(
                onDismiss = onDismiss,
                dialogTitle = stringResource(R.string.loginErrorTitle),
                dialogText = stringResource(R.string.loginErrorMessage)
            )
        }
    }
}

@Composable
fun DisplayLoading(appName: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = appName,
            style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
        )
    }
}



