package com.example.braguia.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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


/*
@Composable
fun CustomAlertDialog(onDismiss: () -> Unit, onExit: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            dismissOnBackPress = false, dismissOnClickOutside = false
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth().padding(0.dp).height(IntrinsicSize.Min),
            elevation = 0.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Text(
                    text = "Logout",
                    modifier = Modifier.padding(8.dp, 16.dp, 8.dp, 2.dp)
                        .align(Alignment.CenterHorizontally).fillMaxWidth(), fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Are you sure you want to logout?",
                    modifier = Modifier.padding(8.dp, 2.dp, 8.dp, 16.dp)
                        .align(Alignment.CenterHorizontally).fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth().width(1.dp))
                Row(Modifier.padding(top = 0.dp)) {
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(
                            onClick = { onDismiss() },
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .weight(1F)
                                .border(0.dp, Color.Transparent)
                                .height(48.dp),
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(text = "Cancel", color = Color.Blue)
                        }
                    }
                    Divider(color = Color.Gray, modifier =
                    Modifier.fillMaxHeight().width(1.dp))
                    CompositionLocalProvider(
                        LocalMinimumTouchTargetEnforcement provides false,
                    ) {
                        TextButton(
                            onClick = {
                                onExit.invoke()
                            },
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp)
                                .weight(1F)
                                .border(0.dp, color = Color.Transparent)
                                .height(48.dp),
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues()
                        ) {
                            Text(text = "Logout", color = Color.Red)
                        }
                    }
                }
            }
        }
    }
}
*/

@Composable
fun AlertConfirmDialog(
    title: String,
    text: String,
    icon: ImageVector = Icons.Filled.Warning,
    cancelOption: () -> Unit,
    cancelText: String,
    confirmOption: () -> Unit,
    confirmText: String,
    onDismiss: () -> Unit
) {

    Dialog(onDismissRequest = onDismiss) {
        Card {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(15.dp)
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                Icon(imageVector = icon, contentDescription = null)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = title,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.W900,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = text)
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Spacer(modifier = Modifier.weight(0.5f))
                    TextButton(onClick = cancelOption) {
                        Text(text = cancelText, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(onClick = confirmOption) {
                        Text(text = confirmText, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }

        }
    }

}

@Composable
@Preview(showSystemUi = true)
fun AlertDialogPrev() {
    Box(modifier = Modifier.fillMaxSize()) {
        AlertConfirmDialog(
            title = "title",
            text = "description",
            cancelOption = {},
            cancelText = "cancelText",
            confirmOption = {},
            confirmText = "confirmText",
            onDismiss = {},
        )
    }
}