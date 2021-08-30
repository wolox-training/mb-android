package ar.com.wolox.android.example.ui.viewpager.random

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentRandomBinding
import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.utils.RequestCode
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import java.util.ArrayList
import javax.inject.Inject

class RandomFragment @Inject constructor() : WolmoFragment<FragmentRandomBinding, RandomPresenter>(), RandomView,
    SwipeRefreshLayout.OnRefreshListener {

    private var isLoading: Boolean = false

    lateinit var layoutManager: LinearLayoutManager

    lateinit var adapter: RandomNewsRecyclerView

    @Inject
    internal lateinit var toastFactory: ToastFactory

    override fun layout() = R.layout.fragment_random

    override fun init() {
        with(binding) {
            layoutManager = LinearLayoutManager(requireContext())
            swipeRefresh.setOnRefreshListener(this@RandomFragment)

            setUpRecycleView()
            presenter.getNewsRequest(false)

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        val visibleItemCount: Int = layoutManager.childCount
                        val pastVisibleItem: Int =
                            layoutManager.findFirstCompletelyVisibleItemPosition()
                        val total: Int = adapter.itemCount

                        if (!isLoading) {
                            if ((visibleItemCount + pastVisibleItem) >= total) {
                                presenter.getNewsRequest(false)
                            }
                        }
                    }
                }
            })
        }
    }

    private fun setUpRecycleView() {
        with(binding) {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = layoutManager
            adapter = RandomNewsRecyclerView()
            recyclerView.adapter = adapter
            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        adapter.setOnItemClickListener(object : RandomNewsRecyclerView.onItemClickListener {
            override fun onItemClick(position: Int, news: News) {
                toastFactory.show(news.commenter)
            }
        })
    }

    override fun getNews(list: ArrayList<News>) {
        adapter.addData(list)
        binding.swipeRefresh.isRefreshing = false
    }

    override fun showLoader(showLoader: Boolean) {
        if (showLoader) {
            isLoading = true
            binding.pbRecyclerView.visibility = View.VISIBLE
        } else {
            isLoading = false
            binding.pbRecyclerView.visibility = View.INVISIBLE
        }
    }

    override fun showError(requestCode: RequestCode) {
        when (requestCode) {
            RequestCode.FAILED -> toastFactory.show(R.string.request_error_validation)
            else -> toastFactory.show(R.string.default_error)
        }
    }

    override fun onRefresh() {
        adapter.clear()
        presenter.getNewsRequest(true)
    }
}
