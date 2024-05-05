package com.example.braguia.viewModel

import android.util.Log
import android.webkit.CookieManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braguia.model.TrailDB
import com.example.braguia.model.User
import com.example.braguia.network.LoginRequest
import com.example.braguia.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
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
                    if (successful == UserLoginState.LoggedIn) {
                        fetchUserInfo()
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
        // trocar variaveis de que user esta logedin
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
    }


    fun getBookmarks() {

        val username: String? = _userUiState.value.user?.username
        if (username != null) {
            viewModelScope.launch {
                userRepository.getBookmarks(username).flowOn(Dispatchers.IO)
                    .collect { bookmarks: List<TrailDB> ->
                        _userUiState.update { currState ->
                            currState.copy(bookmarks = bookmarks.associateBy { it.id })
                        }
                    }
            }
        } else {
            Log.e("USERPAGE", "Invalid username ${_userUiState.value.user}")
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch {
            val username = userRepository.fetchUserInfo()
            if (username != null) {
                val user: User? = userRepository.getUser(username)
                _userUiState.update { currState ->
                    currState.copy(user = user)
                }
            }
        }
    }

    fun updateHistory() {

    }

    fun updateBookmarks() {

    }

    fun toggleBookmark(trailId: Long) {
        viewModelScope.launch {
            _userUiState.value.user?.let {
                if (trailId in _userUiState.value.bookmarks) {
                    userRepository.deleteBookmark(it.username, trailId)
                } else {
                    userRepository.insertBookmark(it.username, trailId)
                }
            }
        }
    }

    fun deleteAllBookmarks() {
        viewModelScope.launch {
            userRepository.deleteAllBookmarks()
        }
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
    val bookmarks: Map<Long, TrailDB> = mapOf(),
    val userLoginState: UserLoginState = UserLoginState.LoggedOut,
    val user: User? = null
)