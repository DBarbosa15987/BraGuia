package com.example.braguia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.TrailCard

@Composable
fun TrailListScreen(
    trails: List<TrailDB>,
    navigateToTrail: (Long) -> Unit,
    innerPadding: PaddingValues,
    toggleBookmark: (Long) -> Unit,
    isBookmarked: (Long) -> Boolean
) {
    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(innerPadding)
    ) {
        items(trails) {
            TrailCard(
                trail = it,
                navigateToTrail = navigateToTrail,
                toggleBookmark = toggleBookmark,
                isBookmark = isBookmarked(it.id),
            )
        }

    }
}
