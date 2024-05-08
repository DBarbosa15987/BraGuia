package com.example.braguia.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog

@Composable
fun WarningDialog(){

    var showing by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { /*TODO*/ }) {

    }
}