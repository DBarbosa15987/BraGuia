package com.example.braguia.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braguia.model.TrailDB
import com.example.braguia.network.LoginRequest
import com.example.braguia.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()

    fun login(username: String, password: String) {
        _userUiState.update { currState ->
            currState.copy(userLoginState = UserLoginState.Loading)
        }
        viewModelScope.launch {
            val loginRequest = LoginRequest(username = username, password = password)

            try {
                userRepository.login(loginRequest) { successful ->
                    _userUiState.update { currState ->
                        currState.copy(userLoginState = successful)
                    }
                }
            } catch (e: Exception) {
                Log.e("USERVIEWMODEL", "Login exception $e")
                userRepository.login(loginRequest) {
                    _userUiState.update { currState ->
                        currState.copy(userLoginState = UserLoginState.Error)
                    }
                }
            }

            Log.i("USERVIEWMODEL_STATE", userUiState.value.userLoginState.toString())
        }
    }

    fun logout() {

    }

    fun fetchUserProfile() {

    }

    fun updateHistory() {

    }

    fun updateBookmarks() {

    }

    fun updateLoggedIn(state: Boolean) {
        //TODO update login in variable
    }

    fun dismissError() {
        _userUiState.update { currState ->
            currState.copy(userLoginState = UserLoginState.LoggedOut)
        }
    }

}

enum class UserLoginState {
    LoggedIn,
    LoggedOut,
    CredentialsWrong,
    Error,
    Loading
}

data class UserUiState(
    val history: List<TrailDB> = listOf(),
    val bookmarks: List<TrailDB> = listOf(),
    val userLoginState: UserLoginState = UserLoginState.LoggedOut,
    val username: String = ""
)