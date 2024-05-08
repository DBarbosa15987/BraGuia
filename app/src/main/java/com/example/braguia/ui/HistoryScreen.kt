package com.example.braguia.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.braguia.model.Bookmark
import com.example.braguia.model.HistoryEntry
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.TrailCard

@Composable
fun HistoryScreen(
    history: List<HistoryEntry>,
    innerPadding: PaddingValues,
    navigateToTrail: (Long) -> Unit,
    toggleBookmark: (Long) -> Unit,
    isBookmarked:(Long) -> Boolean
) {

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(history) { historyEntry ->
            TrailCard(
                trail = historyEntry.trailDB,
                navigateToTrail = navigateToTrail,
                toggleBookmark = toggleBookmark,
                isBookmark = isBookmarked(historyEntry.trailDB.id)
            )
        }
    }
}