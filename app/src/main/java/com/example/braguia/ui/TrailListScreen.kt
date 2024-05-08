package com.example.braguia.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.example.braguia.R
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.AlertDialogTemplate
import com.example.braguia.ui.components.TrailCard

@Composable
fun TrailListScreen(
    trails: List<TrailDB>,
    navigateToTrail: (Long) -> Unit,
    innerPadding: PaddingValues,
    toggleBookmark: (Long) -> Unit,
    googleMapsAskAgain: Boolean,
    dontAskAgain: (Boolean) -> Unit,
    alreadyAskedtoggle: () -> Unit,
    alreadyAsked:Boolean
) {


    var showing by remember { mutableStateOf(true) }
    var checked by remember { mutableStateOf(false) }

    if (showing && googleMapsAskAgain && !alreadyAsked){
        AlertDialogTemplate(
            onDismiss = { showing = false;alreadyAskedtoggle();dontAskAgain(!checked) },
            dialogTitle = stringResource(id = R.string.warningGoogleMaps),
            dialogText = stringResource(id = R.string.warningGoogleMapsText),
            confirmButton = {
                Row {
                    Text(text = stringResource(R.string.dontAskAgainText))
                    Checkbox(checked = checked, onCheckedChange = { checked = it })
                }
            }
        )
    }

    LazyColumn(contentPadding = innerPadding) {
        items(trails) {
            TrailCard(trail = it, navigateToTrail = navigateToTrail, toggleBookmark = toggleBookmark)
        }

    }
}
