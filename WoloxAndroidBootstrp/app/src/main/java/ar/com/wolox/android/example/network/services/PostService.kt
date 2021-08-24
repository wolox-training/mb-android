package ar.com.wolox.android.example.network.services

import ar.com.wolox.android.example.model.LoginResponse
import ar.com.wolox.android.example.model.Post
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.FormUrlEncoded

interface PostService {

    @GET("/posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>

    @FormUrlEncoded
    @POST("/auth/sign_in")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}
