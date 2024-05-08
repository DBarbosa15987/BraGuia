package com.example.braguia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomepageScreen(innerPadding: PaddingValues) {
    // app name
    // app landing page text
    // trail list
    // pin list
    Column(modifier = Modifier.padding(innerPadding)) {
        Text(
            // FIXME change to var
            "BraGuia",
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center,
            fontSize = 50.sp,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            // FIXME change to var
            "Discover the hidden gems of Braga with our virtual guide app! Explore the city's rich history, stunning architecture, and vibrant culture from the palm of your hand. With personalized recommendations and insider tips, you'll experience the best of Braga like a local. Download the app now and start your adventure!",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight()
                    .padding(
                        start = 10.dp, end = 5.dp
                    )
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PinDrop,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                        )
                        Text("Pins")
                    }
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        start = 5.dp, end = 10.dp
                    )
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Route,
                            contentDescription = null,
                            modifier = Modifier.size(50.dp),
                        )
                        Text("Trails")
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePageScreenPreview() {
    HomepageScreen(PaddingValues(2.dp))
}