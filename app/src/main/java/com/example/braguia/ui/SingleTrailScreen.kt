package com.example.braguia.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
    Row {
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
        Text("Name: " + trail.trailName)
        for (reltrail in trail.relTrail) {
            Text(reltrail.attrib + ": " + reltrail.value)
        }
        // TODO: calculate the total distance of the trail
        Text("Difficulty: " + trail.trailDifficulty)
        Text("Duration: " + trail.trailDuration + " min")
        Text("Description: " + trail.trailDesc)
    }
}
