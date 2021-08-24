package ar.com.wolox.android.example.ui.example

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentExampleBinding
import ar.com.wolox.android.example.ui.viewpager.ViewPagerActivity
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import ar.com.wolox.wolmo.core.util.openBrowser
import javax.inject.Inject

class ExampleFragment private constructor() : WolmoFragment<FragmentExampleBinding, ExamplePresenter>(), ExampleView {

    @Inject
    internal lateinit var toastFactory: ToastFactory

    @Inject
    internal lateinit var context: Context

    override fun layout() = R.layout.fragment_example

    override fun init() {
        presenter.autoLogin()
    }

    override fun setListeners() {
        with(binding) {
            etEmail.addTextChangedListener { presenter.onUsernameInputChanged(it.toString()) }
            woloxLink.setOnClickListener { presenter.onWoloxLinkClicked() }
            loginButton.setOnClickListener {
                val email: String = etEmail.text.toString()
                val password: String = etPassword.text.toString()

                if (email.isEmpty() && password.isEmpty()) {
                    etEmail.error = getString(R.string.fragment_example_email_password_empty_field)
                    etEmail.requestFocus()
                    return@setOnClickListener
                }

                if (email.isEmpty()) {
                    etEmail.error = getString(R.string.fragment_example_email_empty_field)
                    etEmail.requestFocus()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    etPassword.error = getString(R.string.fragment_example_password_empty_field)
                    etPassword.requestFocus()
                    return@setOnClickListener
                }

                if (!email.matches(emailPattern.toRegex())) {
                    etEmail.error = getString(R.string.fragment_example_password_invalid)
                }
                presenter.onLoginButtonClicked(email, password)
            }
        }
    }

    override fun toggleLoginButtonEnable(isEnable: Boolean) {
        binding.loginButton.isEnabled = isEnable
    }

    override fun goToViewPager(favouriteColor: String) = ViewPagerActivity.start(requireContext(), favouriteColor)

    override fun showLoader(isVisible: Boolean) {
        if (isVisible)
            binding.pbLoading.visibility = View.VISIBLE
        else
            binding.pbLoading.visibility = View.INVISIBLE
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        connectivityManager.let {

            val capabilities =
                it.getNetworkCapabilities(connectivityManager.activeNetwork)

            capabilities.let {
                return when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        true
                    }
                    else -> false
                }
            }
            return false
        }
    }

    override fun showError(requestCode: RequestCode) {
        when (requestCode) {
            RequestCode.FAILED -> toastFactory.show(R.string.request_error_validation)
            else -> toastFactory.show(R.string.default_error)
        }
    }

    override fun openBrowser(url: String) {

        requireContext().openBrowser(url)
    }

    companion object {
        const val emailPattern = """[a-zA-Z0-9._-]+@[a-z]+\.+[a-z]+"""
        fun newInstance() = ExampleFragment()
    }
}
