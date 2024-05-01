package com.example.braguia.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.braguia.model.TrailDB
import com.example.braguia.network.API
import com.example.braguia.network.LoginRequest
import com.example.braguia.repositories.UserRepository

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var userUiState: UserUiState by mutableStateOf(UserUiState())

    suspend fun login(username: String, password: String) {

        val loginRequest: LoginRequest = LoginRequest(username = username, password = password)
        userRepository.login(loginRequest)

    }

    fun logout() {

    }

    fun fetchUserProfile() {

    }

    fun updateHistory() {

    }

    fun updateBookmarks() {

    }
}

data class UserUiState(
    val history: List<TrailDB> = listOf(),
    val bookmarks: List<TrailDB> = listOf(),
    val username: String = ""
)