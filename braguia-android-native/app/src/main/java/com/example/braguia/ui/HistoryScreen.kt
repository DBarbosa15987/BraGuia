package com.example.braguia.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.braguia.model.HistoryEntry
import com.example.braguia.ui.components.TrailCard
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun HistoryScreen(
    history: List<HistoryEntry>,
    innerPadding: PaddingValues,
    navigateToTrail: (Long) -> Unit,
    toggleBookmark: (Long) -> Unit,
    isBookmarked: (Long) -> Boolean
) {

    LazyColumn(
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier.padding(innerPadding)
    ) {
        items(history) { historyEntry ->
            val dateTime = Date(historyEntry.timeStamp)
            TrailCard(
                trail = historyEntry.trailDB,
                navigateToTrail = navigateToTrail,
                toggleBookmark = toggleBookmark,
                isBookmark = isBookmarked(historyEntry.trailDB.id),
                historyDate = dateTime
            )
        }
    }
}