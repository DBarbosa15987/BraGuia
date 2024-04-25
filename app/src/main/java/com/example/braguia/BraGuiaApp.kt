@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.braguia

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.braguia.viewModel.TrailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.braguia.model.Trail
import com.example.braguia.viewModel.HomeUiState
import com.google.gson.GsonBuilder


@Composable
fun BraGuiaApp() {

    val trailsViewModel: TrailsViewModel =
        viewModel(factory = TrailsViewModel.Factory)

    trailsViewModel.login()
    val uiState = trailsViewModel.homeUiState
    Trails(uiState.trailList)


}





@Composable
fun Trails(trails: List<Trail>) {

    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(trails) {
            TrailCard(trail = it)
        }
    }

}

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
            Log.i("IMG",trail.trailImg)
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


@Composable
@Preview(showSystemUi = true)
fun BraGuiaAppPrev() {

    val lista: MutableList<String> = listOf<String>().toMutableList()
    for (a in 1..100) {
        lista.add(a.toString())
    }
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        //Text(trailsViewModel.homeUiState.contentList.toString())
        //Text(trailsViewModel.homeUiState.trailList.toString())
        items(lista) {
            Card {
                Text(text = it)
            }
        }
    }


}
