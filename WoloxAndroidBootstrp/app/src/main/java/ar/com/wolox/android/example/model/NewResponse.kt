package ar.com.wolox.android.example.model

data class NewsResponse(
    val page: List<News>,
    val count: Long,
    val totalPages: Long,
    val totalCount: Long,
    val currentPage: Long,
    val previousPage: Any? = null,
    val nextPage: Long,
    val nextPageURL: String,
    val previousPageURL: Any? = null
)

data class News(
    val id: Long,
    val commenter: String,
    val comment: String,
    val date: String,
    val avatar: String,
    val likes: List<Long>,
    val createdAt: String,
    val updatedAt: String
)