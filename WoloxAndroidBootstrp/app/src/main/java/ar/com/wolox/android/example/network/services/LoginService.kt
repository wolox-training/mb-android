package ar.com.wolox.android.example.network.services

import ar.com.wolox.android.example.model.LoginResponse
import ar.com.wolox.android.example.model.LoginUserData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/auth/sign_in")
    suspend fun loginUser(@Body loginRequest: LoginUserData): Response<LoginResponse>
}