package com.example.braguia.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.braguia.R

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    appName: String,
    notification: Boolean,
    goToAppInfo: () -> Unit,
    toggleNotification: (Boolean) -> Unit
) {
    // notification
    // location (revogar as permissões)
    // clearCache (media or all)
    // darktheme?
    //
    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        item { NotificationToggle(toggleNotification, notification) }
        item { AboutAppButton(appName, goToAppInfo) }
    }
}

@Composable
fun AboutAppButton(appName: String, goToAppInfo: () -> Unit) {
    Row(modifier = Modifier.clickable { goToAppInfo() }) {
        Column {
            Text(text = stringResource(id = R.string.aboutApp, appName))
        }
    }
}

@Composable
fun NotificationToggle(toggleNotification: (Boolean) -> Unit, notification: Boolean) {


    Row {
        Column {
            Text(text = stringResource(R.string.notifications))
            Text(text = notification.toString())
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = notification,
            onCheckedChange = {
                //checked = it
                toggleNotification(it)
            }
        )

    }


}
