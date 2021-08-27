package ar.com.wolox.android.example.ui.viewpager.random

import androidx.recyclerview.widget.LinearLayoutManager
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentRandomBinding
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import javax.inject.Inject

class RandomFragment @Inject constructor() : WolmoFragment<FragmentRandomBinding, RandomPresenter>(), RandomView {

    override fun layout() = R.layout.fragment_random

    override fun init() {
        val recyclerViewItemList: Array<String> = arrayOf(getString(R.string.fragment_random_recyclerview_array_test_pos_0), getString(
                    R.string.fragment_random_recyclerview_array_test_pos_1), getString(R.string.fragment_random_recyclerview_array_test_pos_2))

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            val randomRecyclerView = RandomNewsRecyclerView(recyclerViewItemList)
            recyclerView.adapter = randomRecyclerView
        }
    }
}
