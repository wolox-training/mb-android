package ar.com.wolox.android.example.ui.news

import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.utils.RequestCode

interface NewsView {
    fun setUpUi(news: News)
    fun showLoader(show: Boolean)
    fun showError(requestCode: RequestCode)
}