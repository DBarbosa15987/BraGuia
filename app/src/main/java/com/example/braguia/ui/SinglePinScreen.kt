package com.example.braguia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.braguia.R
import com.example.braguia.model.Pin
import com.example.braguia.model.RelPin
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.DescriptionShowMore
import com.example.braguia.ui.components.TrailCard
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun SinglePinScreen(
    pin: Pin,
    trails: List<TrailDB>,
    navigateToTrail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    innerPadding: PaddingValues,
    toggleBookmark: (Long) -> Unit,
    isBookmarked: (Long) -> Boolean
) {

    LazyColumn(contentPadding = PaddingValues(10.dp), modifier = modifier.padding(innerPadding)) {
        item { MapWithPin(pin) } // mapa
        item { PinDetails(pin) } // detalhes
        item { } // media
        items(trails) {
            TrailCard(
                trail = it,
                navigateToTrail = navigateToTrail,
                toggleBookmark = toggleBookmark,
                isBookmark = isBookmarked(it.id)
            )
        } // roteiros em que se encontra
    }
}

@Composable
fun MapWithPin(pin: Pin) {
    val pinLatLng = LatLng(pin.pinLat, pin.pinLng)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pinLatLng, 10f)
    }
    GoogleMap(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .fillMaxWidth()
            .height(200.dp),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = MarkerState(position = LatLng(pin.pinLat, pin.pinLng))
        )
    }
}

@Composable
fun PinDetails(pin: Pin, modifier: Modifier = Modifier) {
    Column {
        RelPinsDisplay(relPins = pin.relPin)
        DescriptionShowMore(text = pin.pinDesc, seed = pin.id)
    }
}

@Composable
fun RelPinsDisplay(relPins: List<RelPin>, modifier: Modifier = Modifier) {
    Column {
        for (relPin in relPins) {
            RelPinDisplay(relPin)
        }
    }
}

@Composable
fun RelPinDisplay(relPin: RelPin) {
    if (relPin.value.lowercase() == "yes") {
        Row {
            Icon(imageVector = Icons.Filled.Done, contentDescription = "relPinYesValue")
            Text(text = "  ${relPin.attrib}")
        }
    } else if (relPin.value.lowercase() == "no") {
        Row {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "relPinNoValue")
            Text(text = "  ${relPin.attrib}")
        }
    } else {
        Text(text = stringResource(R.string.relPinAttrib, relPin.attrib, relPin.value))
    }
}


