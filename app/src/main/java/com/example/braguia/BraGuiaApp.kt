@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.braguia

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
