package ar.com.wolox.android.example.network.repository

import ar.com.wolox.android.example.network.services.NewsService
import ar.com.wolox.wolmo.networking.retrofit.RetrofitServices
import ar.com.wolox.wolmo.networking.retrofit.handler.NetworkRequestHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NewsRepository @Inject constructor(private val retrofitServices: RetrofitServices) {

    var page: Int = 1

    var id: Int = 0

    private val service: NewsService
        get() = retrofitServices.getService(NewsService::class.java)

    suspend fun getNews() = withContext(Dispatchers.IO) {
        NetworkRequestHandler.safeApiCall { service.getNews(page) }
    }

    suspend fun getSingleNewsItem() = withContext(Dispatchers.IO) {
        NetworkRequestHandler.safeApiCall { service.getSingleNewsItem(id) }
    }

    suspend fun setLike() = withContext(Dispatchers.IO) {
        NetworkRequestHandler.safeApiCall { service.setLike(id) }
    }
}