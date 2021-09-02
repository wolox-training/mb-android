package ar.com.wolox.android.example.ui.viewpager.random

import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.network.builder.networkRequest
import ar.com.wolox.android.example.network.repository.NewsRepository
import ar.com.wolox.android.example.utils.RequestCode
import ar.com.wolox.wolmo.core.presenter.CoroutineBasePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class RandomPresenter @Inject constructor(private val newsRepository: NewsRepository) : CoroutineBasePresenter<RandomView>() {

    fun getNewsRequest(isOnRefresh: Boolean) = launch {
        if (isOnRefresh) newsRepository.page = 1
        view?.showLoader(true)
        networkRequest(newsRepository.getNews()) {
            onResponseSuccessful { response ->
                val news = response?.page as? ArrayList<News>
                if (news != null) {
                    view?.getNews(news)
                }
                newsRepository.page = newsRepository.page + 1
            }
            onResponseFailed { _, _ -> view?.showError(RequestCode.FAILED) }
            onCallFailure { view?.showError(RequestCode.FATALERROR) }
            view?.showLoader(false)
        }
    }
}
