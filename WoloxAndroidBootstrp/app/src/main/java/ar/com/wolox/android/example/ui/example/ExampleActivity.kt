package ar.com.wolox.android.example.ui.example

import android.content.Context
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.ActivityBaseBinding
import ar.com.wolox.wolmo.core.activity.WolmoActivity
import android.view.WindowManager
import ar.com.wolox.android.example.utils.UserSession
import javax.inject.Inject

class ExampleActivity : WolmoActivity<ActivityBaseBinding>() {

    @Inject
    internal lateinit var userSession: UserSession

    @Inject
    internal lateinit var context: Context

    override fun layout() = R.layout.activity_base

    override fun init() {
        // Hide title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // Hide action bar
        supportActionBar?.hide()

        replaceFragment(binding.activityBaseContent.id, ExampleFragment.newInstance())
    }
}
