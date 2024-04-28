package com.example.braguia.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.braguia.model.TrailDB
import com.example.braguia.repositories.UserRepository

class UserViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    var userUiState: UserUiState by mutableStateOf(UserUiState())

    fun login(){

    }

    fun logout(){

    }

    fun fetchUserProfile(){

    }

    fun updateHistory(){

    }

    fun updateBookmarks(){

    }
}

data class UserUiState(
    val history: List<TrailDB> = listOf(),
    val bookmarks: List<TrailDB> = listOf(),
    val username: String = ""
)