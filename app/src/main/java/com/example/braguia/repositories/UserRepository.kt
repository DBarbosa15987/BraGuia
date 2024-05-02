package com.example.braguia.repositories

import android.util.Log
import com.example.braguia.network.API
import com.example.braguia.network.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UserRepository(
    private val API: API
) {

    suspend fun login(loginRequest: LoginRequest) : Boolean {
        var successful = false
        val call = API.login(loginRequest)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network errors
                throw t
            }

            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                if (response.code() == 200){
                    // login is successful
                    // get cookies and store them with cookie store
                    Log.i("LOGIN","Login successful for user " + loginRequest.username)
                    successful = true
                } else if (response.code() == 400) {
                    // login is unsuccessful
                    // credentials didnt match any user
                    Log.i("LOGIN","Login unsuccessful for user " + loginRequest.username + " due to wrong credentials")
                } else{
                    Log.i("LOGIN","Login unsuccessful for user " + loginRequest.username + " due to a unknown reason")
                }
            }
        }
        )
        return successful
    }

}