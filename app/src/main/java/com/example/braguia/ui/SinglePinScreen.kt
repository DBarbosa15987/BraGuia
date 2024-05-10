package com.example.braguia.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.braguia.R
import com.example.braguia.model.Media
import com.example.braguia.model.Pin
import com.example.braguia.model.RelPin
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.DescriptionShowMore
import com.example.braguia.ui.components.MediaPlayer
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
    isBookmarked: (Long) -> Boolean,
    userType: String
) {

    LazyColumn(contentPadding = PaddingValues(10.dp), modifier = modifier.padding(innerPadding)) {
        item { MapWithPin(pin) } // mapa
        item { PinDetails(pin) } // detalhes
        if (userType.lowercase() == "premium") {
            item { Spacer(modifier = Modifier.height(15.dp)) }
            item {
                MediaSection(
                    title = stringResource(id = R.string.pinMediaTitle),
                    pin = pin
                )
            }
        }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item {
            Text(
                stringResource(R.string.pinFeatured),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                for (trail in trails) {
                    TrailCard(
                        trail = trail,
                        navigateToTrail = navigateToTrail,
                        toggleBookmark = toggleBookmark,
                        isBookmark = isBookmarked(trail.id),
                    )
                }
            }
        }
    }
}


@Composable
fun MediaSection(title: String, pin: Pin) {

    var selectedMedia by remember { mutableStateOf<Media?>(null) }

    Column {
        if (pin.media.isNotEmpty()) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                items(pin.media) { media ->
                    val modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            selectedMedia = media
                        }
                    when (media.mediaType) {
                        "I" -> {
                            AsyncImage(
                                model = media.mediaFile,
                                contentScale = ContentScale.Crop,
                                contentDescription = "PinMedia",
                                placeholder = painterResource(id = R.drawable.loading_img),
                                modifier = modifier,
                                error = painterResource(id = R.drawable.ic_broken_image)
                            )
                        }

                        "V" -> {
                            Icon(
                                imageVector = Icons.Filled.PlayCircleOutline,
                                modifier = modifier,
                                contentDescription = "PlayCircle",
                            )
                        }

                        "R" -> {
                            Icon(
                                imageVector = Icons.Filled.MusicNote,
                                modifier = modifier,
                                contentDescription = "MusicNote",
                            )
                        }
                    }
                }
            }
        }
    }
    selectedMedia?.let { media ->
        Log.i("SINGLETRAILSCREEN", "$media ")
        Dialog(
            onDismissRequest = { selectedMedia = null }
        ) {
            when (media.mediaType) {
                "I" -> {
                    AsyncImage(
                        model = media.mediaFile,
                        modifier = Modifier.fillMaxWidth(),
                        contentDescription = "PinMedia",
                        placeholder = painterResource(id = R.drawable.loading_img),
                        error = painterResource(id = R.drawable.ic_broken_image)
                    )
                }

                "V", "R" -> {
                    MediaPlayer(media = media)
                }
            }
        }
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
fun PinDetails(pin: Pin) {
    Column {
        Text(
            pin.pinName,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        RelPinsDisplay(relPins = pin.relPin)
        DescriptionShowMore(text = pin.pinDesc, seed = pin.id)
    }
}

@Composable
fun RelPinsDisplay(relPins: List<RelPin>) {
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


