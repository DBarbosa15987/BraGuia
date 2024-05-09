package com.example.braguia.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.braguia.R
import com.example.braguia.ui.components.AlertConfirmDialog

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
    val context = LocalContext.current
    var showingDelete by remember { mutableStateOf(false) }
    var showingPreferences by remember { mutableStateOf(false) }
    val modifier = Modifier.padding(10.dp)
    val cardModifier = Modifier.padding(5.dp)
    // location (revogar as permissões)
    LazyColumn(modifier = Modifier.padding(innerPadding), contentPadding = PaddingValues(5.dp)) {
        item {
            SettingsToggle(
                toggle = toggleDarkTheme,
                checked = darkTheme,
                title = stringResource(R.string.darkTheme),
                desc = stringResource(id = R.string.darktThemeToggleDesc),
                modifier = modifier,
                cardModifier = cardModifier
            )
        }
        item {
            if (showingDelete) {
                AlertConfirmDialog(
                    title = stringResource(id = R.string.deleteUserDataTitle),
                    text = stringResource(id = R.string.deleteUserDataDialogText),
                    cancelOption = { showingDelete = false; },
                    cancelText = stringResource(id = R.string.cancel),
                    confirmOption = { showingDelete = false;deleteUserData() },
                    confirmText = stringResource(id = R.string.confirm),
                    onDismiss = { showingDelete = false },
                )
            }
            SettingsButton(
                action = { showingDelete = true },
                title = stringResource(id = R.string.deleteUserDataTitle),
                desc = stringResource(id = R.string.deleteUserDataDesc),
                modifier = modifier,
                cardModifier = cardModifier
            )
        }
        item {
            if (showingPreferences) {
                AlertConfirmDialog(
                    title = stringResource(id = R.string.resetPreferencesTitle),
                    text = stringResource(id = R.string.resetPreferencesDialogText),
                    cancelOption = { showingPreferences = false; },
                    cancelText = stringResource(id = R.string.cancel),
                    confirmOption = { showingPreferences = false;resetPreferences() },
                    confirmText = stringResource(id = R.string.confirm),
                    onDismiss = { showingPreferences = false },
                )
            }
            SettingsButton(
                action = { showingPreferences = true },
                title = stringResource(id = R.string.resetPreferencesTitle),
                desc = stringResource(id = R.string.resetPreferencesDesc),
                modifier = modifier,
                cardModifier = cardModifier
            )
        }
        item {
            SettingsButton(
                action = { openAppSettings(context) },
                title = stringResource(id = R.string.revokePermissionsTitle),
                desc = stringResource(id = R.string.revokePermissionsDesc),
                modifier = modifier,
                cardModifier = cardModifier
            )
        }
        item { AboutAppButton(appName, goToAppInfo, modifier, cardModifier) }
    }
}

private fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    )
    intent.data = Uri.parse(intent.data.toString() + "#permissions")
    Log.i("SETTINGS", "Going to Settings")
    context.startActivity(intent)
}

@Composable
fun AboutAppButton(
    appName: String,
    goToAppInfo: () -> Unit,
    modifier: Modifier,
    cardModifier: Modifier
) {
    Card(modifier = cardModifier.fillMaxWidth()) {
        Row(modifier = modifier
            .fillMaxSize()
            .clickable { goToAppInfo() }) {
            Column {
                Text(
                    text = stringResource(id = R.string.aboutApp, appName),
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
fun SettingsToggle(
    toggle: () -> Unit,
    checked: Boolean,
    title: String,
    desc: String,
    modifier: Modifier,
    cardModifier: Modifier
) {
    Card(modifier = cardModifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(text = title, fontWeight = FontWeight.ExtraBold)
                Text(text = desc)
            }
            Switch(
                modifier = Modifier.align(Alignment.CenterVertically),
                checked = checked,
                onCheckedChange = { toggle() }
            )
        }
    }
}

@Composable
fun SettingsButton(
    action: () -> Unit,
    title: String,
    desc: String,
    modifier: Modifier,
    cardModifier: Modifier
) {
    Card(modifier = cardModifier.fillMaxWidth()) {
        Row(modifier = modifier
            .fillMaxSize()
            .clickable { action() }) {
            Column {
                Text(text = title, fontWeight = FontWeight.ExtraBold)
                Text(text = desc)
            }
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