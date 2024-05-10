package com.example.braguia.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.braguia.R
import com.example.braguia.model.Media
import com.example.braguia.model.Pin
import com.example.braguia.ui.components.MediaPlayer


@Composable
fun MediaGalleryScreen(pins: List<Pin>, innerPadding: PaddingValues) {
    var selectedMedia by remember { mutableStateOf<Media?>(null) }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(10.dp),
        modifier = Modifier.padding(innerPadding)
    ) {
        item {
            val filteredPins = pins.distinctBy { it.id }
            for (pin in filteredPins) {
                if (pin.media.isNotEmpty()) {
                    Text(
                        pin.pinName,
                        style = MaterialTheme.typography.headlineMedium,
                        // FontWeight = FontWeight.Bold
                    )
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