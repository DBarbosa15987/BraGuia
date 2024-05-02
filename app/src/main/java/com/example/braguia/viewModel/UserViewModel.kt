package com.example.braguia.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.braguia.model.TrailDB
import com.example.braguia.network.API
import com.example.braguia.network.LoginRequest
import com.example.braguia.repositories.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var userUiState: UserUiState by mutableStateOf(UserUiState())

    suspend fun login(username: String, password: String) {
        viewModelScope.launch {
            val loginRequest: LoginRequest = LoginRequest(username = username, password = password)
            try {
                userRepository.login(loginRequest)
            } catch (e: Exception){
                Log.e("USERVIEWMODEL", "Login exception $e")
            }
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
}

data class UserUiState(
    val history: List<TrailDB> = listOf(),
    val bookmarks: List<TrailDB> = listOf(),
    val loggedIn : Boolean = false,
    val username: String = ""
)