package com.example.braguia.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.braguia.R

@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    appName: String,
    goToAppInfo: () -> Unit,
    darkTheme: Boolean,
    toggleDarkTheme: () -> Unit,
    deleteUserData: () -> Unit,
    resetPreferences: () -> Unit
) {
    // location (revogar as permissões)
    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        item {
            SettingsToggle(
                toggle = toggleDarkTheme,
                checked = darkTheme,
                title = stringResource(R.string.darkTheme),
                desc = stringResource(id = R.string.darktThemeToggleDesc)
            )
        }
        item {
            SettingsButton(
                action = deleteUserData,
                title = stringResource(id = R.string.deleteUserDataTitle),
                desc = stringResource(id = R.string.deleteUserDataDesc)
            )
        }
        item {
            SettingsButton(
                action = resetPreferences,
                title = stringResource(id = R.string.resetPreferencesTitle),
                desc = stringResource(id = R.string.resetPreferencesDesc)
            )
        }
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
fun SettingsToggle(toggle: () -> Unit, checked: Boolean, title: String, desc: String) {
    Row {
        Column {
            Text(text = title)
            Text(text = desc)
        }
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = { toggle() }
        )
    }
}

@Composable
fun SettingsButton(action: () -> Unit, title: String, desc: String) {
    Row(modifier = Modifier.clickable { action() }) {
        Column {
            Text(text = title)
            Text(text = desc)
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun SettingsScreenPrev() {
    SettingsScreen(
        innerPadding = PaddingValues(0.dp),
        appName = "appName",
        goToAppInfo = {},
        darkTheme = false,
        toggleDarkTheme = {},
        deleteUserData = {},
        resetPreferences = {}
    )
}