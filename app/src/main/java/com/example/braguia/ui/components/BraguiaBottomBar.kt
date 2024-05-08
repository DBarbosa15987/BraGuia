package com.example.braguia.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.braguia.BraguiaScreen

@Composable
fun BraguiaBottomBar(
    currentScreen: BraguiaScreen,
    goHome: () -> Unit,
    goToSettings: () -> Unit,
    goToUserProfile: () -> Unit,
) {

    val modifier = Modifier.scale(1.2f)
    if (currentScreen != BraguiaScreen.Login) {
        BottomAppBar(modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)) {
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically){
                Spacer(modifier = Modifier.weight(0.5f))
                Button(onClick = goToSettings, modifier = modifier) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "BottomBarSettingsIcon"
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = goHome, modifier = modifier) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = "BottomBarHomeIcon")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = goToUserProfile, modifier = modifier) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "BottomBarUserProfileIcon"
                    )
                }
                Spacer(modifier = Modifier.weight(0.5f))
            }
        }
    }
}