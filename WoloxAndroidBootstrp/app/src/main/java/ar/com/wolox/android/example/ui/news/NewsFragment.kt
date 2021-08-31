package ar.com.wolox.android.example.ui.news

import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentNewsBinding
import ar.com.wolox.wolmo.core.fragment.WolmoFragment

class NewsFragment private constructor() : WolmoFragment<FragmentNewsBinding, NewsPresenter>(), NewsView {

    override fun layout() = R.layout.fragment_news

    override fun init() {
    }

    companion object {
        fun newInstance() = NewsFragment()
    }
}