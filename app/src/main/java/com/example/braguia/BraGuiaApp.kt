@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.braguia

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.braguia.ui.TrailList
import com.example.braguia.viewModel.TrailsViewModel


@Composable
fun BraGuiaApp() {

    val trailsViewModel: TrailsViewModel = viewModel(factory = TrailsViewModel.Factory)
    val navController: NavHostController = rememberNavController()


    trailsViewModel.login()
    val uiState = trailsViewModel.homeUiState
    TrailList(uiState.trailList)

}

@Composable
@Preview(showSystemUi = true)
fun BraGuiaAppPrev() {
    val lista: MutableList<String> = listOf<String>().toMutableList()
    for (a in 1..100) {
        lista.add(a.toString())
    }
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        //Text(trailsViewModel.homeUiState.contentList.toString())
        //Text(trailsViewModel.homeUiState.trailList.toString())
        items(lista) {
            Card {
                Text(text = it)
            }
        }
    }
}
