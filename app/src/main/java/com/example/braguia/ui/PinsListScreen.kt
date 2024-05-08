package com.example.braguia.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.braguia.model.Pin
import com.example.braguia.model.PinDB

@Composable
fun PinsListScreen(
    pins: List<PinDB>,
    navigateToPin: (Long) -> Unit,
    innerPadding: PaddingValues,
) {
    LazyColumn(contentPadding = innerPadding) {
        items(pins) {
            PinCard(
                pin = it,
                navigateToPin = navigateToPin,
            )
        }
    }
}

@Composable
fun PinCard(pin: PinDB, navigateToPin: (Long) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(75.dp)
        .clickable { navigateToPin(pin.id) }) {
        Row(modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Filled.PinDrop, contentDescription = "pinicon")
            Spacer(modifier = Modifier.width(10.dp))
            Text(pin.pinName)
        }
    }
}