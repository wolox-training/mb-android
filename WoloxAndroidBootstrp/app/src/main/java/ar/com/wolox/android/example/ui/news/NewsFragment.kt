package ar.com.wolox.android.example.ui.news

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentNewsBinding
import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.utils.Extras
import ar.com.wolox.android.example.utils.RequestCode
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import javax.inject.Inject

class NewsFragment @Inject constructor() : WolmoFragment<FragmentNewsBinding, NewsPresenter>(), NewsView, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    internal lateinit var toastFactory: ToastFactory

    override fun layout() = R.layout.fragment_news

    override fun handleArguments(arguments: Bundle?) = arguments?.containsKey(Extras.News.KEY_NAME)

    override fun init() {
        presenter.onInit(requireArgument(Extras.News.KEY_NAME))
        with(binding) {
            swipeRefreshNewsFragment.setOnRefreshListener(this@NewsFragment)
        }
    }

    override fun setListeners() {
        binding.ButtonNewsCardFav.setOnClickListener {
            it.background = ContextCompat.getDrawable(requireContext(), R.drawable.ic_like_on)
            presenter.setLikeRequest()
        }
        super.setListeners()
    }

    override fun setUpUi(news: News) {
        with(binding) {
            etNewsTitle.text = news.id.toString()
            etNewsDescripction.text = news.comment
            ButtonNewsCardFav

            presenter.setUpLikeButton(news, ButtonNewsCardFav, requireContext())
        }
    }

    override fun onRefresh() {
        presenter.refreshNewsItem()
    }

    override fun showLoader(show: Boolean) {
        if (!show) {
            binding.swipeRefreshNewsFragment.isRefreshing = false
        }
    }

    override fun showError(requestCode: RequestCode) {
        when (requestCode) {
            RequestCode.FAILED -> toastFactory.show(R.string.request_error_validation)
            else -> toastFactory.show(R.string.default_error)
        }
    }

    companion object {
        fun newInstance(news: News) = NewsFragment().apply {
            arguments = bundleOf(Extras.News.KEY_NAME to news)
        }
    }
}