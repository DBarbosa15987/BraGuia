package com.example.braguia.viewModel

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braguia.repositories.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val preferencesRepository: PreferencesRepository
) : ViewModel() {

    init {
        getGoogleAskAgain()
        getIsDarkTheme()
    }

    private val _preferencesUiState = MutableStateFlow(PreferencesUiState())
    val preferencesUiState: StateFlow<PreferencesUiState> = _preferencesUiState.asStateFlow()

    fun selectDarkTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setThemePreference(isDarkTheme)
        }
    }

    fun setGoogleMapsAskAgain(askAgain: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setGoogleMapsAskAgain(askAgain)
        }
    }

    private fun getGoogleAskAgain() {
        viewModelScope.launch {
            preferencesRepository.getIsAskAgain.collect { isAskAgain ->
                _preferencesUiState.update { currState ->
                    currState.copy(isAskAgain = isAskAgain)
                }
            }
        }
    }

    private fun getIsDarkTheme() {
        viewModelScope.launch {
            preferencesRepository.getIsDarkTeme.collect { isDarkTheme ->
                _preferencesUiState.update { currState ->
                    currState.copy(isDarkTheme = isDarkTheme)
                }
            }
        }
    }

    fun resetPreferences() {
        viewModelScope.launch {
            preferencesRepository.resetPreferences()
        }
    }

}

data class PreferencesUiState(
    val isDarkTheme: Boolean = false,
    val isAskAgain: Boolean = true
)