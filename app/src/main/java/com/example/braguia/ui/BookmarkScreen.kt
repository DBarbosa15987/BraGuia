package com.example.braguia.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.TrailCard

@Composable
fun BookmarkScreen(
    bookmarks: List<TrailDB>,
    innerPadding: PaddingValues,
    navigateToTrail: (Long) -> Unit,
    toggleBookmark: (Long) -> Unit,
    isBookmarked:(Long) -> Boolean
) {

    LazyColumn(modifier = Modifier.padding(innerPadding)) {
        items(bookmarks) { bookmark ->
            TrailCard(
                trail = bookmark,
                navigateToTrail = navigateToTrail,
                toggleBookmark = toggleBookmark,
                isBookmark = isBookmarked(bookmark.id)
            )
        }
    }
}