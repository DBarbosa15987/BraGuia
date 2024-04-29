@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.braguia

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.braguia.model.Trail
import com.example.braguia.ui.LoginScreen
import com.example.braguia.ui.SingleTrailScreen
import com.example.braguia.ui.TrailList
import com.example.braguia.viewModel.BraGuiaViewModelProvider
import com.example.braguia.viewModel.TrailsViewModel

enum class BraguiaScreen() {
    Login,
    HomePage,
    Settings,
    UserPage,
    TrailList,
    Trail
}


@Composable
fun BraguiaTopAppBar(
    canNavigateBack: Boolean,
    //currentScreen: BraguiaScreen,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        title = { Text(text = "bomdia") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )

}


@Composable
fun BraGuiaApp() {

    val trailsViewModel: TrailsViewModel = viewModel(factory = BraGuiaViewModelProvider.Factory)

    val navController: NavHostController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()
/*
    val currentScreen =
        BraguiaScreen.valueOf(backStackEntry?.destination?.route ?: BraguiaScreen.HomePage.name)
*/

    Scaffold(topBar = {
        BraguiaTopAppBar(
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() }
        )
    }) { innerPadding ->

        val uiState = trailsViewModel.homeUiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = BraguiaScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BraguiaScreen.Login.name) {
                LoginScreen(uiState.value.appInfo.appName) {
                    navController.navigate(
                        BraguiaScreen.HomePage.name
                    )
                }
            }

            composable(route = BraguiaScreen.HomePage.name) {
                TrailList(
                    trails = uiState.value.trailList,
                    delete = { trailsViewModel.delete() },
                    navigateToTrail = { trailId ->
                        navController.navigate(route = "Trail/$trailId")
                        //navController.navigate(BraguiaScreen.Trail.name)
                    }
                )
            }

            //TODO experimental
            val trailId = "trailId"
            composable(
                route = "Trail/$trailId",
                arguments = listOf(navArgument(trailId) {
                    type = NavType.LongType
                })
            ) { b ->
                Log.i("NAV", "bomdia")
                val id: Long = b.arguments?.getLong("trailId") ?: 1
                trailsViewModel.getTrail(id)
                val trail: Trail? = uiState.value.currTrail
                if (trail != null) {
                    SingleTrailScreen(trail = trail)
                }
            }

        }
    }
}


private fun navigateToHomePage(navController: NavHostController) {
    navController.popBackStack(BraguiaScreen.HomePage.name, inclusive = false)
}


/*
@Composable
@Preview(showSystemUi = true)
fun BraGuiaAppPrev() {
    val lista: MutableList<String> = listOf<String>().toMutableList()
    for (a in 1..100) {
        lista.add(a.toString())
    }

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BraguiaScreen.valueOf(
        backStackEntry?.destination?.route ?: BraguiaScreen.HomePage.name
    )

    Scaffold(topBar = {
        BraguiaTopAppBar(
            canNavigateBack = navController.previousBackStackEntry != null,
            navigateUp = { navController.navigateUp() },
            currentScreen = currentScreen
        )
    }) { innerPadding ->

        LazyColumn(contentPadding = innerPadding) {
            //Text(trailsViewModel.homeUiState.contentList.toString())
            //Text(trailsViewModel.homeUiState.trailList.toString())
            items(lista) {
                Card {
                    Text(text = it)
                }
            }
        }

    }
}
*/
