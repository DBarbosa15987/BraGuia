package com.example.braguia.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.braguia.model.Pin

@Composable
fun PinPreview(
    pin: Pin,
    pinId: Long,
    modifier: Modifier = Modifier,
    navigateToPin: (Long) -> Unit
) {


    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                navigateToPin.invoke(pinId)
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = null)
        Spacer(modifier = Modifier.width(15.dp))
        Text(
            text = pin.pinName,
            fontWeight = FontWeight.Black,
            style = MaterialTheme.typography.titleLarge
        )
    }


}