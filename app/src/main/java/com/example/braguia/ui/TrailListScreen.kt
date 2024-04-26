package com.example.braguia.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.braguia.model.Trail
import com.example.braguia.ui.components.TrailCard

@Composable
fun TrailListScreen(trails: List<Trail>){


}
@Composable
fun TrailList(trails: List<Trail>) {
    LazyColumn(contentPadding = PaddingValues(10.dp)) {
        items(trails) {
            TrailCard(trail = it)
        }
    }
}
