@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.braguia

import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.braguia.model.AppInfo
import com.example.braguia.model.HistoryEntry
import com.example.braguia.model.Pin
import com.example.braguia.model.PinDB
import com.example.braguia.model.Trail
import com.example.braguia.model.TrailDB
import com.example.braguia.model.User
import com.example.braguia.ui.AppInfoScreen
import com.example.braguia.ui.BookmarkScreen
import com.example.braguia.ui.HistoryScreen
import com.example.braguia.ui.HomepageScreen
import com.example.braguia.ui.LoginScreen
import com.example.braguia.ui.MediaGalleryScreen
import com.example.braguia.ui.PinsListScreen
import com.example.braguia.ui.SettingsScreen
import com.example.braguia.ui.SinglePinScreen
import com.example.braguia.ui.SingleTrailScreen
import com.example.braguia.ui.TrailListScreen
import com.example.braguia.ui.UserPageScreen
import com.example.braguia.ui.components.BraguiaBottomBar
import com.example.braguia.ui.components.BraguiaTopAppBar
import com.example.braguia.ui.theme.BraGuiaTheme
import com.example.braguia.viewModel.BraGuiaViewModelProvider
import com.example.braguia.viewModel.PreferencesUiState
import com.example.braguia.viewModel.PreferencesViewModel
import com.example.braguia.viewModel.TrailsViewModel
import com.example.braguia.viewModel.UserViewModel

enum class BraguiaScreen {
    Login,
    HomePage,
    Settings,
    UserPage,
    Trail,
    TrailList,
    Pin,
    AppInfo,
    History,
    Bookmarks,
    Pins,
    Media
}

@Composable
fun App() {

    val preferencesViewModel: PreferencesViewModel =
        viewModel(factory = BraGuiaViewModelProvider.Factory)
    val preferencesUiState = preferencesViewModel.preferencesUiState.collectAsState()

    BraGuiaTheme(darkTheme = preferencesUiState.value.isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            BraGuiaApp(preferencesViewModel, preferencesUiState)
        }
    }
}

@Composable
fun BraGuiaApp(
    preferencesViewModel: PreferencesViewModel,
    preferencesUiState: State<PreferencesUiState>
) {

    val toggleTheme =
        { preferencesViewModel.selectDarkTheme(!preferencesUiState.value.isDarkTheme) }
    var askPermission by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        askPermission = true
        if (isGranted) {
            // Permission Accepted: Do something
            Log.d("ExampleScreen", "PERMISSION GRANTED")

        } else {
            // Permission Denied: Do something
            Log.d("ExampleScreen", "PERMISSION DENIED")
        }
    }

    val context = LocalContext.current

    val trailsViewModel: TrailsViewModel = viewModel(factory = BraGuiaViewModelProvider.Factory)
    val userViewModel: UserViewModel = viewModel(factory = BraGuiaViewModelProvider.Factory)

    val trailsUiState = trailsViewModel.homeUiState.collectAsState()
    val userUiState = userViewModel.userUiState.collectAsState()

    val navController: NavHostController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentRoute = backStackEntry?.destination?.route ?: BraguiaScreen.HomePage.name
    val currentScreen = when {
        currentRoute.startsWith(BraguiaScreen.Trail.name) -> BraguiaScreen.Trail
        currentRoute.startsWith(BraguiaScreen.Pin.name) -> BraguiaScreen.Pin
        else -> BraguiaScreen.valueOf(currentRoute)
    }
    val geofenceHelper = GeofenceHelper(context)
    val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }
    if (trailsUiState.value.pinList.isNotEmpty()) {
        geofenceHelper.addGeofences(trailsUiState.value.pinList, geofencePendingIntent, context)
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
                }
            )
        },
        topBar = {
            BraguiaTopAppBar(
                canNavigateBack = navController.previousBackStackEntry != null && currentScreen != BraguiaScreen.HomePage,
                navigateUp = { navController.navigateUp() },
                currentScreen = currentScreen,
                userLoginState = userUiState.value.userLoginState
            )
        }) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BraguiaScreen.Login.name,
            modifier = Modifier
        ) {
            composable(route = BraguiaScreen.Login.name) {
                val appInfo: AppInfo? = trailsUiState.value.appInfo
                if (appInfo != null) {
                    LoginScreen(
                        appName = appInfo.appName,
                        login = userViewModel::login,
                        onDismiss = userViewModel::dismissError,
                        userLoginState = userUiState.value.userLoginState,
                        grantAccess = { navController.navigate(BraguiaScreen.HomePage.name) },
                        googleMapsAskAgain = preferencesUiState.value.isAskAgain,
                        dontAskAgain = preferencesViewModel::setGoogleMapsAskAgain,
                        alreadyAsked = userUiState.value.warningAsked,
                        alreadyAskedtoggle = { userViewModel.alreadyAskedtoggle() }
                    )
                }
            }

            composable(route = BraguiaScreen.HomePage.name) {
                trailsViewModel.getTrails()
                val appInfo: AppInfo? = trailsUiState.value.appInfo
                if (appInfo != null) {
                    when (PackageManager.PERMISSION_GRANTED) {
                        ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) -> {
                            // Some works that require permission
                            Log.d("ExampleScreen", "Code requires permission")
                        }

                        else -> {
                            // Asking for permission
                            launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                    HomepageScreen(
                        appInfo = appInfo,
                        innerPadding = innerPadding,
                        navigateToPins = { navController.navigate(BraguiaScreen.Pins.name) },
                        navigateToTrails = { navController.navigate(BraguiaScreen.TrailList.name) },
                        navigateToBookmarks = { navController.navigate(BraguiaScreen.Bookmarks.name) },
                        navigateToHistory = { navController.navigate(BraguiaScreen.History.name) }
                    )
                }
            }

            composable(route = BraguiaScreen.Pins.name) {
                trailsViewModel.getAllPins()

                val pins: List<PinDB> = trailsUiState.value.pinList
                PinsListScreen(
                    pins = pins,
                    navigateToPin = { pinId ->
                        navController.navigate("${BraguiaScreen.Pin.name}/$pinId")
                    },
                    innerPadding = innerPadding
                )
            }

            composable(route = BraguiaScreen.TrailList.name) {
                userViewModel.getBookmarks()
                TrailListScreen(
                    trails = trailsUiState.value.trailList,
                    navigateToTrail = { trailId ->
                        navController.navigate("${BraguiaScreen.Trail.name}/$trailId")
                    },
                    innerPadding = innerPadding,
                    toggleBookmark = userViewModel::toggleBookmark,
                    isBookmarked = { trailId ->
                        trailId in userUiState.value.bookmarks
                    }
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
                val trail: Trail? = trailsUiState.value.currTrail
                val user: User? = userUiState.value.user
                if (trail != null && user != null) {
                    trailsViewModel.getTrailRoute(trail)
                    SingleTrailScreen(
                        trail = trail,
                        route = trailsUiState.value.trailRoute,
                        innerPadding = innerPadding,
                        navigateToPin = { pinId ->
                            navController.navigate("${BraguiaScreen.Pin.name}/$pinId")
                        },
                        updateHistory = userViewModel::updateHistory,
                        userType = user.userType,
                        navigateToMedia = { navController.navigate(BraguiaScreen.Media.name) }
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
                userViewModel.getBookmarks()
                val user: User? = userUiState.value.user
                if (pin != null && user != null) {
                    SinglePinScreen(
                        pin = pin,
                        innerPadding = innerPadding,
                        navigateToTrail = { trailId ->
                            navController.navigate("${BraguiaScreen.Trail.name}/$trailId")
                        },
                        trails = trails,
                        toggleBookmark = userViewModel::toggleBookmark,
                        isBookmarked = { trailId ->
                            trailId in userUiState.value.bookmarks
                        },
                        userType = user.userType,//TODO isto resulta????
                        navigateToMedia = {navController.navigate(BraguiaScreen.Media.name)}//TODO isto resulta????
                    )
                }
            }

            composable(route = BraguiaScreen.Media.name) {
                val pins: List<Pin> = trailsUiState.value.trailRoute
                MediaGalleryScreen(pins, innerPadding)
            }

            composable(
                route = BraguiaScreen.Settings.name
            ) {
                val appInfo: AppInfo? = trailsUiState.value.appInfo
                if (appInfo != null) {
                    SettingsScreen(
                        appName = appInfo.appName,
                        innerPadding = innerPadding,
                        goToAppInfo = { navController.navigate(BraguiaScreen.AppInfo.name) },
                        darkTheme = preferencesUiState.value.isDarkTheme,
                        toggleDarkTheme = toggleTheme,
                        resetPreferences = preferencesViewModel::resetPreferences,
                        deleteUserData = {
                            userViewModel.deleteUserData()
                            preferencesViewModel.resetPreferences()
                        }
                    )
                }
            }
            composable(
                route = BraguiaScreen.AppInfo.name
            ) {
                val appInfo: AppInfo? = trailsUiState.value.appInfo
                if (appInfo != null) {
                    AppInfoScreen(appInfo = appInfo, innerPadding)
                }
            }

            composable(
                route = BraguiaScreen.UserPage.name
            ) {
                val user: User? = userUiState.value.user
                if (user != null) {
                    UserPageScreen(
                        innerPadding = innerPadding,
                        user = user,
                        logOff = {
                            userViewModel.logout()
                            navController.navigate(BraguiaScreen.Login.name)
                        }
                    )
                }
            }

            composable(
                route = BraguiaScreen.Bookmarks.name
            ) {
                userViewModel.getBookmarks()
                val bookmarks: List<TrailDB> = userUiState.value.bookmarks.values.toList()
                BookmarkScreen(
                    bookmarks = bookmarks,
                    innerPadding = innerPadding,
                    navigateToTrail = { trailId ->
                        navController.navigate("${BraguiaScreen.Trail.name}/$trailId")
                    },
                    toggleBookmark = userViewModel::toggleBookmark,
                    isBookmarked = { trailId ->
                        trailId in userUiState.value.bookmarks
                    }
                )

            }

            composable(
                route = BraguiaScreen.History.name
            ) {
                userViewModel.getHistory()
                userViewModel.getBookmarks()
                val history: List<HistoryEntry> = userUiState.value.history
                HistoryScreen(
                    history = history,
                    innerPadding = innerPadding,
                    navigateToTrail = { trailId ->
                        navController.navigate("${BraguiaScreen.Trail.name}/$trailId")
                    },
                    toggleBookmark = userViewModel::toggleBookmark,
                    isBookmarked = { trailId ->
                        trailId in userUiState.value.bookmarks
                    }
                )
            }
        }
    }
}
