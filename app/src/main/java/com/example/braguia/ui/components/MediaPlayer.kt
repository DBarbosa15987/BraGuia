package com.example.braguia.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.braguia.model.Media

@SuppressLint("OpaqueUnitKey")
@Composable
fun MediaPlayer(media: Media) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp), color = Color.Black
    ) {
        val url = media.mediaFile
        val context = LocalContext.current
        val exoPlayer = ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(url))
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

