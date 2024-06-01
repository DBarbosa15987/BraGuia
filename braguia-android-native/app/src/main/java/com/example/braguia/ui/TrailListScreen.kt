package com.example.braguia.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.TrailCard

@Composable
fun TrailListScreen(trails: List<TrailDB>, navigateToTrail:(Long) -> Unit,innerPadding: PaddingValues) {
    LazyColumn(contentPadding = innerPadding) {
        items(trails) {
            TrailCard(trail = it, navigateToTrail= navigateToTrail )
        }
    }
}
