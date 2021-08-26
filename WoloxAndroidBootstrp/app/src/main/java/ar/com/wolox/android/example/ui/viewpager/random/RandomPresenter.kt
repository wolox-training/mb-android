package ar.com.wolox.android.example.ui.viewpager.random

import ar.com.wolox.wolmo.core.presenter.BasePresenter
import ar.com.wolox.wolmo.core.util.Logger
import ar.com.wolox.wolmo.core.util.ToastFactory
import javax.inject.Inject

class RandomPresenter @Inject constructor(private val toastFactory: ToastFactory) : BasePresenter<RandomView>() {

    @Inject
    lateinit var logger: Logger

    override fun onViewAttached() {
        logger.d("View Attached")
    }

    companion object {
        private const val NUMBER_MIN = 1
        private const val NUMBER_MAX = 35000
        private const val TAG = "RandomPresenter"
    }
}
