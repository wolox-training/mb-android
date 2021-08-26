package ar.com.wolox.android.example.ui.viewpager.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.viewpager.widget.ViewPager
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentViewpagerBinding
import ar.com.wolox.android.example.ui.viewpager.random.RandomFragment
import ar.com.wolox.android.example.ui.viewpager.request.RequestFragment
import ar.com.wolox.android.example.utils.Extras.ViewPager.FAVOURITE_COLOR_KEY
import ar.com.wolox.wolmo.core.adapter.viewpager.SimpleFragmentPagerAdapter
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import com.google.android.material.snackbar.Snackbar
import dagger.Lazy
import javax.inject.Inject

class ViewPagerFragment private constructor() : WolmoFragment<FragmentViewpagerBinding, ViewPagerPresenter>(), ViewPagerView {

    // Lazy example, a lazy injection does not build the dependencies until #get() is called
    @Inject
    internal lateinit var randomFragment: Lazy<RandomFragment>

    @Inject
    internal lateinit var requestFragment: RequestFragment

    override fun layout() = R.layout.fragment_viewpager

    override fun handleArguments(arguments: Bundle?) = arguments?.containsKey(FAVOURITE_COLOR_KEY)

    override fun init() {
        binding.viewPager.adapter = SimpleFragmentPagerAdapter(childFragmentManager).apply {
            addFragments(
                randomFragment.get() to getString(R.string.fragment_viewpager_tab_news),
                requestFragment to getString(R.string.fragment_viewpager_tab_profile))
        }

        binding.tabLayout.apply {
            setupWithViewPager(binding.viewPager)
            getTabAt(TAB_NEWS)?.setIcon(R.drawable.ic_list)
            getTabAt(TAB_PROFILE)?.setIcon(R.drawable.ic_person)
        }

        presenter.onInit(requireArgument(FAVOURITE_COLOR_KEY))

        val fab: View = binding.fab
        fab.setOnClickListener { view ->
            Snackbar.make(view, getString(R.string.fragment_viewpager_snackbar_test), Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    override fun setListeners() {
        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                presenter.onSelectedTab(position)
            }
        })
        presenter.onSelectedTab(0)
    }

    override fun showUserAndFavouriteColor(username: String, favouriteColor: String) {
        binding.viewPagerTitle.text = resources.getString(R.string.view_pager_title, username, favouriteColor)
    }

    override fun setToolbarTitle(title: ViewPagerToolbarTitle) {
        val titleRes = when (title) {
            ViewPagerToolbarTitle.RANDOM -> R.string.random_toolbar_title
            ViewPagerToolbarTitle.REQUEST -> R.string.request_toolbar_title
        }
    }

    companion object {
        const val TAB_NEWS = 0
        const val TAB_PROFILE = 1

        fun newInstance(favouriteColor: String) = ViewPagerFragment().apply {
            arguments = bundleOf(FAVOURITE_COLOR_KEY to favouriteColor)
        }
    }
}

enum class ViewPagerToolbarTitle { RANDOM, REQUEST }
