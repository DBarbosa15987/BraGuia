package com.example.braguia.viewModel


import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.braguia.BraGuiaApplication
import com.example.braguia.model.AppInfo
import com.example.braguia.model.Media
import com.example.braguia.model.TrailDB
import com.example.braguia.repositories.AppInfoRepository
import com.example.braguia.repositories.TrailRepository
import com.example.braguia.repositories.UserRepository
import kotlinx.coroutines.launch

class TrailsViewModel(
    val trailRepository: TrailRepository,/*FIXME SO POR ENQUANTO*/
    val appInfoRepository: AppInfoRepository,
    val userRepository: UserRepository
) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(
        HomeUiState(
            appInfo =
            AppInfo(
                "", "", listOf(), listOf(),
                listOf(), ""
            )
        )
    )
        private set

    init {
        //TODO temp
        viewModelScope.launch {
            appInfoRepository.fetchAppInfo()
            trailRepository.fetchAPI()
        }
        login()
    }

    /*    fun login(): ResponseBody? {

            var response:ResponseBody? = null
            viewModelScope.launch {

                val loginRequest: LoginRequest =
                    LoginRequest("premium_user", "premium@email.com", "paiduser")
                response = userRepository.login(loginRequest)
                Log.i("LOGIN",response)

            }

            return response
        }*/

    fun login() {

        //Logica de login getTrailsPreview
        getTrails()
        getAppInfo()


    }

    fun getTrails() {

        viewModelScope.launch {
            homeUiState = try {
                val result = trailRepository.getTrailsPreview()
                HomeUiState(result, homeUiState.mediaList, homeUiState.appInfo)
            } catch (e: Exception) {
                Log.e("TRAILS", e.toString())
                Log.e("TRAILS", e.stackTraceToString())
                HomeUiState(homeUiState.trailList, homeUiState.mediaList, homeUiState.appInfo)
            }
        }
    }

    fun getAppInfo() {

        viewModelScope.launch {
            homeUiState = try {
                val response = appInfoRepository.getAppInfo()
                HomeUiState(homeUiState.trailList, homeUiState.mediaList, response)
            } catch (e: Exception) {
                Log.e("APPINFO", e.toString())
                //Log.e("APPINFO", e.stackTraceToString())
                HomeUiState(homeUiState.trailList, homeUiState.mediaList, homeUiState.appInfo)
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BraGuiaApplication)
                val trailRepository = application.container.trailRepository
                val appInfoRepository = application.container.appInfoRepository
                val userRepository = application.container.userRepository
                TrailsViewModel(trailRepository, appInfoRepository, userRepository)
            }
        }
    }

}

data class HomeUiState(
    val trailList: List<TrailDB> = listOf(),
    val mediaList: List<Media> = listOf(),
    val appInfo: AppInfo,
    val test: String = "bomdia"
)