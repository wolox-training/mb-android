package ar.com.wolox.android.example.ui.news

import android.os.Bundle
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.ActivityBaseBinding
import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.utils.Extras
import ar.com.wolox.wolmo.core.activity.WolmoActivity

class NewsActivity : WolmoActivity<ActivityBaseBinding>() {

    override fun layout() = R.layout.activity_base

    override fun handleArguments(arguments: Bundle?) = arguments?.containsKey(Extras.News.KEY_NAME)

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        replaceFragment(binding.activityBaseContent.id, NewsFragment.newInstance(requireArgument(Extras.News.KEY_NAME)))
    }
}