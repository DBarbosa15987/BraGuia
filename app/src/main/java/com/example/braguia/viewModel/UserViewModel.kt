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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UserViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var userUiState: UserUiState by mutableStateOf(UserUiState())

    fun login(username: String, password: String, callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            val loginRequest = LoginRequest(username = username, password = password)
            try {
                val successful = userRepository.login(loginRequest)
                callback(successful)
            } catch (e: Exception) {
                Log.e("USERVIEWMODEL", "Login exception $e")
                callback(false) // or handle error differently
            }
        }
    }

//    fun login(username: String, password: String): Boolean {
//        var successful = false
//        runBlocking {
//            val loginCoroutine = viewModelScope.launch {
//                val loginRequest: LoginRequest =
//                    LoginRequest(username = username, password = password)
//                try {
//                    successful = userRepository.login(loginRequest)
//                } catch (e: Exception) {
//                    Log.e("USERVIEWMODEL", "Login exception $e")
//                }
//            }
//            loginCoroutine.join()
//        }
//        Log.i("USERVIEWMODEL","successful = $successful")
//        return successful
//    }

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
    val loggedIn: Boolean = false,
    val username: String = ""
)