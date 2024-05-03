package com.example.braguia.repositories

import android.util.Log
import com.example.braguia.network.API
import com.example.braguia.network.LoginRequest
import com.example.braguia.viewModel.UserLoginState
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val API: API
) {

    fun login(loginRequest: LoginRequest,callback: (UserLoginState) -> Unit) {
        val call = API.login(loginRequest)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback(UserLoginState.Error)
                throw t
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if (response.code() == 200){
                    // login is successful
                    // get cookies and store them with cookie store
                    Log.i("LOGIN","Login successful for user " + loginRequest.username)
                    callback(UserLoginState.LoggedIn)
                } else if (response.code() == 400) {
                    // login is unsuccessful
                    // credentials didnt match any user
                    Log.i("LOGIN","Login unsuccessful for user " + loginRequest.username + " due to wrong credentials")
                    callback(UserLoginState.CredentialsWrong)
                } else{
                    Log.i("LOGIN","Login unsuccessful for user " + loginRequest.username + " due to a unknown reason")
                    callback(UserLoginState.Error)
                }
            }
        }
        )
    }
}