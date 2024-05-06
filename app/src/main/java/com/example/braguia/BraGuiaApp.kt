@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.braguia

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.braguia.model.AppInfo
import com.example.braguia.model.Pin
import com.example.braguia.model.Trail
import com.example.braguia.model.TrailDB
import com.example.braguia.ui.AppInfoScreen
import com.example.braguia.ui.LoginScreen
import com.example.braguia.ui.SinglePinScreen
import com.example.braguia.ui.SingleTrailScreen
import com.example.braguia.ui.TrailListScreen
import com.example.braguia.ui.components.TrailCard
import com.example.braguia.viewModel.BraGuiaViewModelProvider
import com.example.braguia.viewModel.TrailsViewModel
import com.example.braguia.viewModel.UserViewModel
import com.google.android.gms.location.GeofencingClient

enum class BraguiaScreen {
    Login,
    HomePage,
    Settings,
    UserPage,
    Trail,
    Pin
}

@Composable
fun BraguiaTopAppBar(
    canNavigateBack: Boolean,
    currentScreen: BraguiaScreen,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {

    TopAppBar(
        title = { Text(text = currentScreen.name) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun BraguiaBottomBar(
    currentScreen: BraguiaScreen,
    goHome: () -> Unit,
    goToSettings: () -> Unit,
    goToUserProfile: () -> Unit,
    deleteAllBookmarks: () -> Unit
) {
    if (currentScreen != BraguiaScreen.Login) {
        BottomAppBar {
            Button(onClick = goHome) {
                Icon(imageVector = Icons.Filled.Home, contentDescription = "BottomBarHomeIcon")
            }
            Button(onClick = goToSettings) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "BottomBarSettingsIcon"
                )
            }
            Button(onClick = goToUserProfile) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "BottomBarUserProfileIcon"
                )
            }
            Button(onClick = deleteAllBookmarks) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
        }
    }
}


@Composable
fun BraGuiaApp(geofenceClient: GeofencingClient) {

    val trailsViewModel: TrailsViewModel = viewModel(factory = BraGuiaViewModelProvider.Factory)
    val userViewModel: UserViewModel = viewModel(factory = BraGuiaViewModelProvider.Factory)

    val navController: NavHostController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route ?: BraguiaScreen.HomePage.name
    val currentScreen = when {
        currentRoute.startsWith(BraguiaScreen.Trail.name) -> BraguiaScreen.Trail
        currentRoute.startsWith(BraguiaScreen.Pin.name) -> BraguiaScreen.Pin
        else -> BraguiaScreen.valueOf(currentRoute)
    }

    Scaffold(
        bottomBar = {
            BraguiaBottomBar(
                currentScreen = currentScreen,
                goHome = {
                    navController.popBackStack(
                        BraguiaScreen.HomePage.name,
                        inclusive = false
                    )
                },
                goToSettings = {
                    navController.navigate(
                        BraguiaScreen.Settings.name
                    )
                },
                goToUserProfile = {
                    navController.navigate(
                        BraguiaScreen.UserPage.name
                    )
                },
                //TODO temp!!!!
                userViewModel::deleteAllBookmarks
            )
        },
        topBar = {
            BraguiaTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen != BraguiaScreen.HomePage,
                navigateUp = { navController.navigateUp() },
                currentScreen = currentScreen
            )
        }) { innerPadding ->

        val trailsUiState = trailsViewModel.homeUiState.collectAsState()
        val userUiState = userViewModel.userUiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = BraguiaScreen.Login.name,
            modifier = Modifier
        ) {
            composable(route = BraguiaScreen.Login.name) {
                LoginScreen(
                    appName = trailsUiState.value.appInfo.appName,
                    login = userViewModel::login,
                    logout = userViewModel::logout,
                    onDismiss = userViewModel::dismissError,
                    userLoginState = userUiState.value.userLoginState,
                    grantAccess = { navController.navigate(BraguiaScreen.HomePage.name) }
                )
            }

            composable(route = BraguiaScreen.HomePage.name) {
                trailsViewModel.getTrails()
                TrailListScreen(
                    trails = trailsUiState.value.trailList,
                    navigateToTrail = { trailId ->
                        navController.navigate("${BraguiaScreen.Trail.name}/$trailId")
                    },
                    innerPadding = innerPadding,
                    toggleBookmark = userViewModel::toggleBookmark
                )
            }

            composable(
                route = "${BraguiaScreen.Trail.name}/{trailId}",
                arguments = listOf(navArgument("trailId") {
                    type = NavType.LongType
                })
            ) { b ->
                val id: Long = b.arguments?.getLong("trailId") ?: 0 // TODO tratamento de errors?
                trailsViewModel.getTrail(id)
                trailsViewModel.getEdges(id)
                val trail: Trail? = trailsUiState.value.currTrail // TODO tratamento de errors?
                if (trail != null) {
                    trailsViewModel.getTrailRoute(trail)
                    SingleTrailScreen(
                        trail = trail,
                        route = trailsUiState.value.trailRoute,
                        innerPadding = innerPadding,
                        navigateToPin = { pinId ->
                            navController.navigate("${BraguiaScreen.Pin.name}/$pinId")
                        }
                    )
                }
            }
            composable(
                route = "${BraguiaScreen.Pin.name}/{pinId}",
                arguments = listOf(navArgument("pinId") {
                    type = NavType.LongType
                })
            ) { b ->
                val pinId: Long = b.arguments?.getLong("pinId") ?: 0 // TODO tratamento de errors?
                trailsViewModel.getPin(pinId)
                val pin: Pin? = trailsUiState.value.currPin
                trailsViewModel.getPinTrails(pinId)
                val trails: List<TrailDB> = trailsUiState.value.trailList
                if (pin != null) {
                    SinglePinScreen(
                        pin = pin,
                        innerPadding = innerPadding,
                        navigateToTrail = { trailId ->
                            navController.navigate("${BraguiaScreen.Trail.name}/$trailId")
                        },
                        trails = trails,
                        toggleBookmark = userViewModel::toggleBookmark
                    )
                }
            }
            composable(
                route = BraguiaScreen.Settings.name
            ) {
                val appInfo: AppInfo = trailsUiState.value.appInfo
                AppInfoScreen(appInfo = appInfo, innerPadding)
            }

            composable(
                route = BraguiaScreen.UserPage.name
            ) {
                userViewModel.getBookmarks()
                val bookmarks: List<TrailDB> = userUiState.value.bookmarks.values.toList()
                LazyColumn(Modifier.padding(innerPadding)) {
                    items(bookmarks) { trail ->
                        TrailCard(
                            trail = trail,
                            navigateToTrail = { trailId ->
                                navController.navigate("${BraguiaScreen.Trail.name}/$trailId")
                            },
                            toggleBookmark = userViewModel::toggleBookmark
                        )
                    }
                }
            }
        }
    }
}
