package com.example.braguia.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.braguia.R
import com.example.braguia.model.TrailDB

@Composable
fun TrailCard(
    trail: TrailDB,
    modifier: Modifier = Modifier,
    navigateToTrail: (Long) -> Unit,
    toggleBookmark: (Long) -> Unit,
    isBookmark: Boolean
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(MaterialTheme.shapes.small)
            .clickable { navigateToTrail.invoke(trail.id) }
    ) {
        Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.fillMaxSize()) {
            BookmarkButton(isBookmark, toggleBookmark, trail.id)
            Row {
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
                Column(
                    modifier = modifier
                        .padding(8.dp)
                        .weight(0.5f)
                ) {

                    Text(text = trail.trailName, style = MaterialTheme.typography.titleLarge)
                    Text(text = trail.trailDuration.toString() + " min")
                    Text(text = trail.trailDifficulty)

                }
            }
        }
    }
}

@Composable
fun BookmarkButton(isBookmarked: Boolean, toggleBookmark: (Long) -> Unit, trailId: Long) {
    if (isBookmarked) {
        IconButton(onClick = { toggleBookmark(trailId) }) {
            Icon(
                imageVector = Icons.Outlined.Bookmark,
                contentDescription = "BookmakedIcon"
            )
        }
    } else {
        IconButton(onClick = { toggleBookmark(trailId) }) {
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = "NotBookmakedIcon"
            )
        }
    }
}

