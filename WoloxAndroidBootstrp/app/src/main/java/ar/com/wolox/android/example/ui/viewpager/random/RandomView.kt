package ar.com.wolox.android.example.ui.viewpager.random

import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.utils.RequestCode
import java.util.ArrayList

interface RandomView {
    fun getNews(list: ArrayList<News>)
    fun showLoader(showLoader: Boolean)
    fun showError(requestCode: RequestCode)
}
