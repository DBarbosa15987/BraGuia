package com.example.braguia.ui

import android.content.Intent
import androidx.compose.material.icons.filled.MusicNote
import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.media3.common.MediaItem.fromUri
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.braguia.R
import com.example.braguia.model.Media
import com.example.braguia.model.Pin
import com.example.braguia.model.Trail
import com.example.braguia.ui.components.AlertDialogTemplate
import com.example.braguia.ui.components.DescriptionShowMore
import com.example.braguia.ui.components.EdgePreviewCard
import com.example.braguia.ui.components.MediaPlayer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun SingleTrailScreen(
    route: List<Pin>,
    trail: Trail,
    innerPadding: PaddingValues,
    navigateToPin: (Long) -> Unit,
    updateHistory: (Long) -> Unit,
    userType: String,
    navigateToMedia: () -> Unit

) {
    LazyColumn(contentPadding = PaddingValues(10.dp), modifier = Modifier.padding(innerPadding)) {
        item {
            TrailInformation(
                trail = trail,
                route = route,
                updateHistory = updateHistory,
                userType = userType,
                navigateToMedia = navigateToMedia
            )
        }
        item { Spacer(modifier = Modifier.height(15.dp)) }
        item { MapWithPins(route) }
        item { Spacer(modifier = Modifier.height(5.dp)) }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                for (i in 0..<trail.edges.size) {
                    val title = "Stop ${i + 1}"
                    EdgePreviewCard(edge = trail.edges[i], title, navigateToPin = navigateToPin)
                }
            }
        }
    }
}

@Composable
fun MapWithPins(pins: List<Pin>) {
    if (pins.isNotEmpty()) {
        val firstPinLatLng = LatLng(pins[0].pinLat, pins[0].pinLng)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(firstPinLatLng, 15f)
        }
        GoogleMap(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
                .height(400.dp), cameraPositionState = cameraPositionState
        ) {
            for (pin in pins) {
                Marker(state = MarkerState(LatLng(pin.pinLat, pin.pinLng)), title = pin.pinName)
            }
        }
    }
}

@Composable
fun TrailInformation(
    trail: Trail,
    route: List<Pin>,
    updateHistory: (Long) -> Unit,
    userType: String,
    navigateToMedia: () -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = trail.trailImg,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
                .clip(
                    MaterialTheme.shapes.medium
                ),
            contentScale = ContentScale.Crop,
            contentDescription = "Trail Image",
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image)
        )
        Text(
            trail.trailName,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(0.5f))
            StartTrailButton(trail.id, route, context, updateHistory)
            Spacer(modifier = Modifier.weight(1f))
            MediaButton(userType, navigateToMedia)
            Spacer(modifier = Modifier.weight(0.5f))
        }
        for (reltrail in trail.relTrail) {
            Text(reltrail.attrib + ": " + reltrail.value)
        }
        Text(stringResource(id = R.string.trailDifficulty, trail.trailDifficulty))
        Text(stringResource(id = R.string.trailDuration, trail.trailDuration))
        DescriptionShowMore(
            stringResource(id = R.string.trailDesc, trail.trailDesc),
            seed = trail.id
        )
    }
}

@Composable
fun MediaButton(userType: String, navigateToMedia: () -> Unit) {

    var showing by remember { mutableStateOf(false) }

    if (showing) {
        AlertDialogTemplate(
            onDismiss = { showing = false },
            dialogTitle = stringResource(id = R.string.accessDenied),
            dialogText = stringResource(id = R.string.accessDeniedText)
        )
    }

    Button(onClick = {
        if (userType.lowercase() == "premium") {
            navigateToMedia.invoke()
        } else {
            showing = true
        }
    }) {
        Text("Check Media")
    }

}


@Composable
fun StartTrailButton(
    trailId: Long,
    route: List<Pin>,
    context: Context,
    updateHistory: (Long) -> Unit
) {
    Button(onClick = {
        // launch google maps
        val mapIntentUriString = createMapsRouteUriString(route)
        Log.i("SINGLETRAILSCREEN", "MapIntentUriString = $mapIntentUriString")
        val mapIntentUri = Uri.parse(mapIntentUriString)
        val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
        try {
            updateHistory(trailId)
            context.startActivity(mapIntent)
        } catch (e: Exception) {
            Log.e("START_TRAIL", "Trail start intent failed")
        }
    }) {
        Text("Start Trail")
    }
}

fun createMapsRouteUriString(route: List<Pin>): String {
    val lastPin = route.last()
    var destination = "destination=" + lastPin.pinLat.toString() + "," + lastPin.pinLng.toString()
    var waypoints = "waypoints="
    for (i in 0..<route.size - 1) {
        waypoints += route[i].pinLat.toString() + "," + route[i].pinLng.toString()
        if (i < route.size - 2) waypoints += "|"
    }
    return "https://www.google.com/maps/dir/?api=1&$waypoints&$destination"
}