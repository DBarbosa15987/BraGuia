package com.example.braguia.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.example.braguia.R
import com.example.braguia.model.Trail

@Composable
fun SingleTrailScreen(trail: Trail) {
    Row {
        TrailInformation(trail = trail)
    }

}

@Composable
fun TrailInformation(trail: Trail) {
    Column {
        AsyncImage(
            model = trail.trailImg,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.5f),
            contentScale = ContentScale.Crop,
            contentDescription = "Trail Image",
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image)
        )
        // FIXME mudar para o titulo do screen
        Text(stringResource(id = R.string.trailName,trail.trailName))
        for (reltrail in trail.relTrail) {
            Text(reltrail.attrib + ": " + reltrail.value)
        }
        // TODO: calculate the total distance of the trail
        Text(stringResource(id = R.string.trailDifficulty,trail.trailDifficulty))
        Text(stringResource(id = R.string.trailDuration,trail.trailDuration))
        Text(stringResource(id = R.string.trailDesc,trail.trailDesc))
    }
}
