package ar.com.wolox.android.example.network.services

import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {
    @GET("/comments")
    suspend fun getNews(@Query("page")pageIndex: Int): Response<NewsResponse>

    @GET("/comments/{id}")
    suspend fun getSingleNewsItem(@Path("id") id: Int?): Response<News>

    @PUT("/comments/like/{id}")
    suspend fun setLike(@Path("id") id: Int?): Response<NewsResponse>
}