package com.example.braguia.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.braguia.R

@Composable
fun LoginScreen(
    appName:String,
    grantAccess: () -> Unit,
    settings: () -> Unit
) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        LoginPrompt(appName,grantAccess,settings)
    }
}

@Composable
fun LoginPrompt(
    appName:String,
    grantAccess: () -> Unit,
    settings: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

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
            isError = error,
            singleLine = true,
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
            isError = error,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))
        Button(
            //TODO validação
            onClick = {
                grantAccess()
                /*if (username == "a" && password == "a") {
                    grantAccess()
                } else {
                    error = true
                    username = ""
                    password = ""
                }*/
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(stringResource(id = R.string.Login))
        }
        Button(onClick = settings) {
            Text(text = "settings")
        }
    }
}
