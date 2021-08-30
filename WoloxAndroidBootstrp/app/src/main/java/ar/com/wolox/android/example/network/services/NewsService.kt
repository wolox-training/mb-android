package ar.com.wolox.android.example.network.services

import ar.com.wolox.android.example.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/comments")
    suspend fun getNews(@Query("page")pageIndex: Int): Response<NewsResponse>
}