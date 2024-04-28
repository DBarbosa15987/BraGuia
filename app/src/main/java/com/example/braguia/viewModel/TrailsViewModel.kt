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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrailsViewModel(
    private val trailRepository: TrailRepository,/*FIXME SO POR ENQUANTO*/
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()


    init {
        viewModelScope.launch {
            appInfoRepository.fetchAppInfo()
            trailRepository.fetchAPI()
        }
        getTrails()
        getAppInfo()
    }
    

    fun getTrails() {
        viewModelScope.launch {
            trailRepository.getTrailsPreview().flowOn(Dispatchers.IO)
                /*.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = listOf()
            )*/
                .collect { trails: List<TrailDB> ->
                    _homeUiState.update { curr -> curr.copy(trailList = trails) }
                }
        }

        /*        viewModelScope.launch {
                    var result = listOf<TrailDB>()
                    try {
                        result = trailRepository.getTrailsPreview()
                    } catch (e: Exception) {
                        Log.e("TRAILS", e.toString())
                    }
                    _homeUiState.update { currentState ->
                        currentState.copy(
                            trailList = result
                        )
                    }
                }*/

    }

    fun delete() {
        viewModelScope.launch {
            trailRepository.trailDAO.deleteAll()
        }
    }

    fun getAppInfo() {

        viewModelScope.launch {
            var result = AppInfo("", "", listOf(), listOf(), listOf(), "")
            try {
                result = appInfoRepository.getAppInfo()
            } catch (e: Exception) {
                Log.e("APPINFO", e.toString())
            }
            _homeUiState.update { currentState ->
                currentState.copy(appInfo = result)
            }
        }
    }

}

data class HomeUiState(
    val trailList: List<TrailDB> = listOf(),
    val mediaList: List<Media> = listOf(),
    val appInfo: AppInfo = AppInfo("", "", listOf(), listOf(), listOf(), "")
)

