package com.example.braguia.repositories

import com.example.braguia.network.API
import com.example.braguia.network.LoginRequest
import com.google.gson.JsonObject
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class UserRepository(
    private val API: API
) {

    suspend fun login(loginRequest: LoginRequest) {

        /*val call = API.login(loginRequest)
        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body()
                    // Handle successful login
                    // Access JSON properties using jsonResponse.getAs<Type>("property_name")
                } else {
                    // Handle failed login
                    val errorMessage = response.errorBody()?.string()
                    // Parse error message
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // Handle network errors
            }
        }
        )*/

    }

}