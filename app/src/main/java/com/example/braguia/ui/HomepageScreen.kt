package com.example.braguia.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.braguia.model.AppInfo

@Composable
fun HomepageScreen(
    appInfo: AppInfo,
    innerPadding: PaddingValues,
    navigateToPins: () -> Unit,
    navigateToTrails: () -> Unit,
    navigateToBookmarks: () -> Unit,
    navigateToHistory: () -> Unit
) {
    val spanCount: Int = 2
    LazyVerticalGrid(
        modifier = Modifier.padding(innerPadding),
        columns = GridCells.Fixed(spanCount),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item(
            span = {
                GridItemSpan(currentLineSpan = spanCount)
            }
        ){
            Column{
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                )
                Text(
                    appInfo.appName,
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.Center,
                    fontSize = 60.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                )
                Text(
                    appInfo.landingPageText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
            }
        }

        item {
            CardButton("Pins", Icons.Filled.PinDrop, navigateToPins)
        }
        item {
            CardButton("Trails", Icons.Filled.Route, navigateToTrails)
        }
        item {
            CardButton("Bookmarks", Icons.Filled.Bookmarks, navigateToBookmarks)
        }
        item {
            CardButton("History", Icons.Filled.History, navigateToHistory)
        }
    }
    /*Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
                Text(
                    appInfo.appName,
                    fontFamily = FontFamily.Cursive,
                    textAlign = TextAlign.Center,
                    fontSize = 60.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                )
                Text(
                    appInfo.landingPageText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )*/

}

@Composable
fun CardButton(textContent: String, icon: ImageVector, navigate: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable { navigate() }, contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                )
                Text(textContent)
            }
        }
    }

}

/*
@Preview(showSystemUi = true)
@Composable
fun HomePageScreenPreview() {
    HomepageScreen(
        AppInfo(
            appName = "BraGuia",
            appDesc = "",
            contacts = listOf(),
            partners = listOf(),
            socials = listOf(),
            landingPageText = "Discover the hidden gems of Braga with our virtual guide app! Explore the city's rich history, stunning architecture, and vibrant culture from the palm of your hand. With personalized recommendations and insider tips, you'll experience the best of Braga like a local. Download the app now and start your adventure!"
        ), PaddingValues(2.dp)
    )
}*/
