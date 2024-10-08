package com.example.braguia.repositories

import android.util.Log
import com.example.braguia.model.Bookmark
import com.example.braguia.model.HistoryEntry
import com.example.braguia.model.HistoryEntryDB
import com.example.braguia.model.TrailDB
import com.example.braguia.model.User
import com.example.braguia.model.dao.BookmarkDAO
import com.example.braguia.model.dao.HistoryEntryDAO
import com.example.braguia.model.dao.UserDAO
import com.example.braguia.network.API
import com.example.braguia.network.LoginRequest
import com.example.braguia.viewModel.UserLoginState
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val API: API,
    private val userDAO: UserDAO,
    private val bookmarkDAO: BookmarkDAO,
    private val historyEntryDAO: HistoryEntryDAO
) {


    suspend fun checkLoggedInUser(): String {
        val users = userDAO.getLoggedInUser()
        var username:String = ""
        try {
            username = users[0].username
        }catch (e:Exception){
            Log.i("LOGIN", "No one is logged in")
        }
        return username
    }

    suspend fun updateLoggedIn(user: User) {
        userDAO.updateLoggedIn(user)
    }

    fun login(loginRequest: LoginRequest, callback: (UserLoginState) -> Unit) {
        val call = API.login(loginRequest)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback(UserLoginState.Error)
                throw t
            }

            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    // login is successful
                    // get cookies and store them with cookie store
                    Log.i("LOGIN", "Login successful for user " + loginRequest.username)
                    callback(UserLoginState.LoggedIn)

                } else if (response.code() == 400) {
                    // login is unsuccessful
                    // credentials didnt match any user
                    Log.i(
                        "LOGIN",
                        "Login unsuccessful for user " + loginRequest.username + " due to wrong credentials"
                    )
                    callback(UserLoginState.CredentialsWrong)
                } else {
                    Log.i(
                        "LOGIN",
                        "Login unsuccessful for user " + loginRequest.username + " due to a unknown reason"
                    )
                    callback(UserLoginState.Error)
                }
            }
        })
    }

    suspend fun logout(user: User) {
        Log.i("USERREPOSITORY", "Reached logout")
        val call = API.logout()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                throw t
            }

            override fun onResponse(
                call: Call<ResponseBody>, response: Response<ResponseBody>
            ) {
                if (response.code() == 200) {
                    Log.i("LOGOUT", "Logout successful")
                } else {
                    Log.i(
                        "LOGOUT", "Logout unsuccessful"
                    )
                }
            }
        })
        user.loggedIn = false
        userDAO.updateLoggedIn(user)
    }

    suspend fun fetchUserInfo(): String? {
        var username: String? = null
        try {
            val user: User = API.getUserInfo()
            userDAO.insert(user)
            username = user.username
            Log.i("USER_INSERTED", user.toString())
        } catch (e: Exception) {

            Log.e("FETCH_USER", e.toString())
        }
        return username
    }

    suspend fun getUser(username: String): User? {
        var user: User? = null
        try {
            user = userDAO.getUser(username)
            Log.i("FETCH_USER_SUCCESSFUL", user.toString())
        } catch (e: Exception) {
            Log.e("FETCH_USER", e.toString())
        }
        return user
    }

    fun getBookmarks(username: String): Flow<List<TrailDB>> {
        return bookmarkDAO.getBookmarksFromUser(username)
    }

    suspend fun insertBookmark(username: String, trailId: Long) {
        val bookmark = Bookmark(username = username, trailId = trailId)
        try {
            bookmarkDAO.insert(bookmark)
        } catch (e: Exception) {
            Log.e("BOOKMARK", "${e}_${bookmark}")
        }
    }

    suspend fun deleteBookmark(username: String, trailId: Long) {
        bookmarkDAO.delete(username, trailId)
    }

    suspend fun updateHistory(historyEntryDB: HistoryEntryDB) {
        historyEntryDAO.insert(historyEntryDB)
    }

    fun getHistory(username: String): Flow<List<HistoryEntry>> {
        return historyEntryDAO.getHistory(username)
    }

    suspend fun deleteUserInfo(username: String){
        bookmarkDAO.deleteAllFromUser(username)
        historyEntryDAO.deleteAllFromUser(username)
    }

}