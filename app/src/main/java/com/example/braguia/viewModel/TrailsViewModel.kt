package com.example.braguia.viewModel


import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braguia.GeofenceBroadcastReceiver
import com.example.braguia.model.AppInfo
import com.example.braguia.model.Edge
import com.example.braguia.model.Media
import com.example.braguia.model.Pin
import com.example.braguia.model.PinDB
import com.example.braguia.model.Trail
import com.example.braguia.model.TrailDB
import com.example.braguia.repositories.AppInfoRepository
import com.example.braguia.repositories.TrailRepository
import com.google.android.gms.location.GeofencingClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrailsViewModel(
    private val trailRepository: TrailRepository,
    private val appInfoRepository: AppInfoRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()


    init {
        viewModelScope.launch {
            appInfoRepository.fetchAppInfo()
            trailRepository.fetchAPI()
            getAppInfo()
            getTrails()
            getAllPins()
        }
    }

    fun getAllPins() {
        viewModelScope.launch {
            val pins = trailRepository.getAllPins()
            _homeUiState.update { currState ->
                currState.copy(pinList = pins)
            }
        }
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
    }

    fun getPinTrails(pinId: Long) {
        viewModelScope.launch {
            var result = listOf<TrailDB>()
            try {
                result = trailRepository.getPinTrails(pinId)
            } catch (e: Exception) {
                Log.e("PIN_TRAILS", e.toString())
            }
            _homeUiState.update { curr ->
                curr.copy(trailList = result)
            }
        }
    }

    fun getTrailRoute(trail: Trail) {
        val route = trailRepository.getTrailRoute(trail)
        _homeUiState.update { curr ->
            curr.copy(trailRoute = route)
        }
    }

    fun delete() {
        viewModelScope.launch {
            trailRepository.trailDAO.deleteAll()
        }
    }

    fun getAppInfo() {

        viewModelScope.launch {
            var result: AppInfo? = null
            try {
                result = appInfoRepository.getAppInfo()
            } catch (e: Exception) {
                Log.e("APPINFO", e.toString())
            }
            _homeUiState.update { curr ->
                curr.copy(appInfo = result)
            }
        }
    }

    fun getTrail(trailId: Long) {
        viewModelScope.launch {
            val result = trailRepository.getTrail(trailId)
            _homeUiState.update { currState ->
                currState.copy(currTrail = result)
            }
        }
    }

    fun getEdges(trailId: Long) {
        viewModelScope.launch {
            val result = trailRepository.getEdges(trailId)
            _homeUiState.update { currState ->
                currState.copy(edgeList = result)
            }
        }
    }

    fun getPin(pinId: Long) {
        viewModelScope.launch {
            val result = trailRepository.getPin(pinId)
            _homeUiState.update { curState ->
                curState.copy(currPin = result)
            }
        }
    }
}

data class HomeUiState(
    val trailList: List<TrailDB> = listOf(),
    val pinList: List<PinDB> = listOf(),
    val edgeList: List<Edge> = listOf(),
    val trailRoute: List<Pin> = listOf(),
    val currPin: Pin? = null,
    val currTrail: Trail? = null,
    val mediaList: List<Media> = listOf(),
    val appInfo: AppInfo? = null
)
