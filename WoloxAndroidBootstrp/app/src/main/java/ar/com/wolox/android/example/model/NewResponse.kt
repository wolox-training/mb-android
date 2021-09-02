package ar.com.wolox.android.example.model

import java.io.Serializable

class NewsResponse(
    val page: List<News>,
    val message: String,
    val count: Long,
    val totalPages: Long,
    val totalCount: Long,
    val currentPage: Long,
    val previousPage: Any? = null,
    val nextPage: Long,
    val nextPageURL: String,
    val previousPageURL: Any? = null
) : Serializable

class News(
    val id: Long,
    val commenter: String,
    val comment: String,
    val date: String,
    val avatar: String,
    val likes: List<Long>,
    val createdAt: String,
    val updatedAt: String
) : Serializable
