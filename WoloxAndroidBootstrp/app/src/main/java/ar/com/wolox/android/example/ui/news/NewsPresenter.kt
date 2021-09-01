package ar.com.wolox.android.example.ui.news

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import ar.com.wolox.android.R
import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.network.builder.networkRequest
import ar.com.wolox.android.example.network.repository.NewsRepository
import ar.com.wolox.android.example.utils.RequestCode
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.wolmo.core.presenter.CoroutineBasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewsPresenter @Inject constructor(private val newsRepository: NewsRepository, private val userSession: UserSession) : CoroutineBasePresenter<NewsView>() {

    fun onInit(news: News) {
        newsRepository.id = news.id.toInt()
        view?.setUpUi(news)
    }

    fun refreshNewsItem() = launch {
        networkRequest(newsRepository.getSingleNewsItem()) {
            onResponseSuccessful { _ ->
            }
            onResponseFailed { _, _ -> view?.showError(RequestCode.FAILED) }
            onCallFailure { view?.showError(RequestCode.FATALERROR) }
        }
        view?.showLoader(false)
    }

    fun setLikeRequest() = launch {
        networkRequest(newsRepository.setLike()) {
            onResponseSuccessful { _ ->
            }
            onResponseFailed { _, _ -> view?.showError(RequestCode.FAILED) }
            onCallFailure { view?.showError(RequestCode.FATALERROR) }
        }
    }

    fun setUpLikeButton(news: News, likeButton: Button, context: Context) {

        if (news.likes.isNotEmpty()) {
            if (news.likes.contains(userSession.id?.toLong())) {
                likeButton.background = ContextCompat.getDrawable(context, R.drawable.ic_like_on)
            } else {
                likeButton.background = ContextCompat.getDrawable(context, R.drawable.ic_like_off)
            }
        }
    }
}