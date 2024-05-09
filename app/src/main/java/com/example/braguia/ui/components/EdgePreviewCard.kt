package com.example.braguia.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.braguia.model.Edge
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun EdgePreviewCard(
    edge: Edge,
    title: String,
    modifier: Modifier = Modifier,
    navigateToPin: (Long) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Card(modifier = modifier.clip(MaterialTheme.shapes.small)) {
        Column(
            modifier = modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Text(title, style = MaterialTheme.typography.titleLarge)
                    Text("Duration: ${edge.edgeDuration} minutes")
                }
                Spacer(modifier = Modifier.weight(1f))
                SeeMoreButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if (expanded) {
                PinPreview(
                    pin = edge.edgeStart,
                    navigateToPin = navigateToPin,
                    pinId = edge.edgeStart.id
                )
                Spacer(modifier = Modifier.height(10.dp))
                PinPreview(
                    pin = edge.edgeEnd,
                    navigateToPin = navigateToPin,
                    pinId = edge.edgeEnd.id
                )
            }

        }
    }

}

@Composable
fun SeeMoreButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = null,
        )
    }

}