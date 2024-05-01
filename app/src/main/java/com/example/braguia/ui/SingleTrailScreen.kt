package com.example.braguia.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.braguia.R
import com.example.braguia.model.Edge
import com.example.braguia.model.Trail
import com.example.braguia.ui.components.EdgePreviewCard

@Composable
fun SingleTrailScreen(
    trail: Trail,
    edges: List<Edge>,
    innerPadding: PaddingValues,
    navigateToPin: (Long) -> Unit
) {

    LazyColumn(Modifier.padding(innerPadding)) {
        item { TrailInformation(trail = trail) }
        items(edges) { edge ->
            EdgePreviewCard(edge = edge, navigateToPin = navigateToPin)
        }
    }

}

@Composable
fun TrailInformation(trail: Trail) {
    Column {
        AsyncImage(
            model = trail.trailImg,
            modifier = Modifier
                .fillMaxSize(),
            //.weight(0.5f),
            contentScale = ContentScale.Crop,
            contentDescription = "Trail Image",
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image)
        )
        Log.i("IMG2", trail.trailImg)
        // FIXME mudar para o titulo do screen
        Text(stringResource(id = R.string.trailName, trail.trailName))
        for (reltrail in trail.relTrail) {
            Text(reltrail.attrib + ": " + reltrail.value)
        }
        // TODO: calculate the total distance of the trail
        Text(stringResource(id = R.string.trailDifficulty, trail.trailDifficulty))
        Text(stringResource(id = R.string.trailDuration, trail.trailDuration))
        Text(stringResource(id = R.string.trailDesc, trail.trailDesc))
    }
}
