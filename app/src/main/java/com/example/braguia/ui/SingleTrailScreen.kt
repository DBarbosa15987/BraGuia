package com.example.braguia.ui

import android.content.Intent
import androidx.compose.material.icons.filled.MusicNote
import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
import com.example.braguia.ui.components.EdgePreviewCard
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
    updateHistory: (Long) -> Unit
) {
    LazyColumn(Modifier.padding(innerPadding)) {
        item { TrailInformation(trail = trail, route = route, updateHistory = updateHistory) }
        item { MapWithPins(route) }
        items(trail.edges) { edge ->
            EdgePreviewCard(edge = edge, navigateToPin = navigateToPin)
        }
        item { MediaGalleryScreen(pins = route) }
    }
}


@SuppressLint("OpaqueUnitKey")
@Composable
fun MediaPlayer(media: Media) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp), color = Color.Black
    ) {
        val url = media.mediaFile
        val context = LocalContext.current
        val exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(fromUri(url))
            repeatMode = Player.REPEAT_MODE_OFF
            playWhenReady = false
            prepare()
        }

        DisposableEffect(
            AndroidView(factory = {
                PlayerView(context).apply {
                    useController = true

                    player = exoPlayer
                }
            })
        ) {
            onDispose { exoPlayer.release() }
        }
    }
}


@Composable
fun MediaGalleryScreen(pins: List<Pin>) {
    Column {
        var displayMedia by remember { mutableStateOf<Media?>(null) }
        var parentId by remember { mutableLongStateOf(-1) }
        var showing by remember { mutableStateOf(false) }
        val filteredPins = pins.distinctBy { it.id }
        for (pin in filteredPins) {
            if (pin.media.isNotEmpty()) {
                Text(
                    pin.pinName,
                    style = MaterialTheme.typography.headlineMedium,
//                fontWeight = FontWeight.Bold
                )
                LazyRow {
                    items(pin.media) { media ->
                        val modifier = Modifier
                            .size(100.dp)
                            .clickable {
                                showing = true
                                parentId = pin.id
                                displayMedia = media
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
                Log.i("SINGLETRAILSCREEN", "$displayMedia $parentId")
                Log.i("SINGLETRAILSCREEN", "$showing")
                if (displayMedia != null && !showing) {
                    showing = true
                    Dialog(onDismissRequest = {
                        displayMedia = null
                        showing = false
                    }) {
                        when (displayMedia?.mediaType) {
                            "I" -> {
                                AsyncImage(
                                    model = displayMedia?.mediaFile,
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = "PinMedia",
                                    placeholder = painterResource(id = R.drawable.loading_img),
                                    error = painterResource(id = R.drawable.ic_broken_image)
                                )
                            }

                            "V" -> {
                                MediaPlayer(media = displayMedia!!)
                            }

                            "R" -> {
                                MediaPlayer(media = displayMedia!!)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MapWithPins(pins: List<Pin>) {
    //TODO calculate the center point of all pins so everyone appears in the map
    //FIXME isto deu index out of bounds...
    val firstPinLatLng = LatLng(pins[0].pinLat, pins[0].pinLng)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(firstPinLatLng, 10f)
    }
    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp), cameraPositionState = cameraPositionState
    ) {
        for (pin in pins) {
            Marker(state = MarkerState(LatLng(pin.pinLat, pin.pinLng)), title = pin.pinName)
        }
    }
}

@Composable
fun TrailInformation(trail: Trail, route: List<Pin>, updateHistory: (Long) -> Unit) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = trail.trailImg,
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterHorizontally)
                .clip(
                    RoundedCornerShape(16.dp)
                ),
            //.weight(0.5f),
            contentScale = ContentScale.Crop,
            contentDescription = "Trail Image",
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image)
        )
        Log.i("IMG2", trail.trailImg)
        // FIXME mudar para o titulo do screen
        Text(
            trail.trailName,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineLarge
        )
        for (reltrail in trail.relTrail) {
            Text(reltrail.attrib + ": " + reltrail.value)
        }
        // TODO: calculate the total distance of the trail
        Text(stringResource(id = R.string.trailDifficulty, trail.trailDifficulty))
        Text(stringResource(id = R.string.trailDuration, trail.trailDuration))
        Text(stringResource(id = R.string.trailDesc, trail.trailDesc))
        Button(onClick = {
            // launch google maps
            val mapIntentUriString = createMapsRouteUriString(route)
            Log.i("SINGLETRAILSCREEN", "MapIntentUriString = $mapIntentUriString")
            val mapIntentUri = Uri.parse(mapIntentUriString)
            val mapIntent = Intent(Intent.ACTION_VIEW, mapIntentUri)
            try {
                updateHistory(trail.id)
                context.startActivity(mapIntent)

            } catch (e: Exception) {
                // TODO: add error dialog
            }
        }) {
            Text("Start Trail")
        }
    }
}

// FIXME change the location of this function
fun createMapsRouteUriString(route: List<Pin>): String {
    // origin=Google+Pyrmont+NSW&destination=QVB&destination_place_id=ChIJISz8NjyuEmsRFTQ9Iw7Ear8&travelmode=walking
    val lastPin = route.last()
    var destination = "destination=" + lastPin.pinLat.toString() + "," + lastPin.pinLng.toString()
    var waypoints = "waypoints="
    for (i in 0..<route.size - 1) {
        waypoints += route[i].pinLat.toString() + "," + route[i].pinLng.toString()
        if (i < route.size - 2) waypoints += "|"
    }
    return "https://www.google.com/maps/dir/?api=1&$waypoints&$destination"
}