package com.example.braguia.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.braguia.R

@Composable
fun AlertDialogTemplate(
    onDismiss: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmButton: @Composable () -> Unit = {}
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "ErrorAlertDialogIcon"
            )
        },
        title = { Text(text = dialogTitle) },
        text = { Text(text = dialogText) },
        onDismissRequest = { onDismiss() },
        confirmButton = confirmButton,
        dismissButton = {
            TextButton(onClick = { onDismiss() }, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.Dismiss), textAlign = TextAlign.Center)
            }
        }
    )
}