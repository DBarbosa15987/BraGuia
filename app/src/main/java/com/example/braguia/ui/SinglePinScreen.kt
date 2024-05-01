package com.example.braguia.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.braguia.model.Pin

@Composable
fun SinglePinScreen(pin: Pin, modifier: Modifier = Modifier) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column{
            Text(
                text = "Era o que faltava...",
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.displayLarge
            )
            Text(text = "Btw este é o pin ${pin.pinName}")
        }
    }

}