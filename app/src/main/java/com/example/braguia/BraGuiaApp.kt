@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.braguia

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.example.braguia.viewModel.TrailsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun BraGuiaApp() {

    val trailsViewModel: TrailsViewModel =
        viewModel(factory = TrailsViewModel.Factory)
    Column(){
            Text(trailsViewModel.homeUiState.contentList.toString())
            Text(trailsViewModel.homeUiState.trailList.toString())
            Text(trailsViewModel.homeUiState.test)
            Text("brruh")
        }


}
