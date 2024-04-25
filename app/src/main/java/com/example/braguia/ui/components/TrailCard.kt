package com.example.braguia.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.braguia.R
import com.example.braguia.model.Trail

@Composable
fun TrailCard(trail: Trail, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(MaterialTheme.shapes.small)
            .padding(PaddingValues(8.dp))
    ) {
        Row {
            Column(
                modifier = modifier
                    .padding(8.dp)
                    .weight(0.5f)
            ) {

                Text(text = trail.trailName)
                Text(text = trail.trailDuration.toString() + " min")
                Text(text = trail.trailDifficulty)

            }
            Log.i("IMG", trail.trailImg)
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(trail.trailImg)
                    .build(),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.5f),
                contentScale = ContentScale.Crop,
                contentDescription = "Trail Image",
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image)
            )
        }
    }
}
