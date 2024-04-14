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
import com.example.braguia.model.Content
import com.example.braguia.model.Trail
import com.example.braguia.repositories.TrailRepository
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class TrailsViewModel(private val trailRepository: TrailRepository) : ViewModel() {

    var homeUiState: HomeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        getTrails()
    }

    fun getTrails() {

        viewModelScope.launch {
            homeUiState = try {
                val result = trailRepository.getTrails()
                HomeUiState(result, listOf())
            } catch (e: Exception) {
                Log.e("TRAILS", e.toString())
                HomeUiState(listOf())
            }
        }

    }

    fun getContent() {

        viewModelScope.launch {
            homeUiState = try {
                val response = trailRepository.getContent()
                HomeUiState(listOf(), response)
            } catch (e: Exception) {
                Log.e("CONTENT", e.toString())
                HomeUiState(listOf())
            }
        }

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BraGuiaApplication)
                val trailRepository = application.container.trailRepository
                //application.database.trailDAO()
                TrailsViewModel(trailRepository = trailRepository)
            }
        }
    }


}

data class HomeUiState(
    val trailList: List<Trail> = listOf(),
    val contentList: List<Content> = listOf(),
    val test: String = "bomdia"
)