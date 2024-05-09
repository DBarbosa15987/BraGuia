package com.example.braguia.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.braguia.R
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.components.AlertDialogTemplate
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
                isBookmark = isBookmarked(it.id)
            )
        }

    }
}
