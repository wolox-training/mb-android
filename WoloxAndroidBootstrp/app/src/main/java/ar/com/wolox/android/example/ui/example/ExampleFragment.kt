package ar.com.wolox.android.example.ui.example

import android.view.View
import androidx.core.widget.addTextChangedListener
import ar.com.wolox.android.R
import ar.com.wolox.android.databinding.FragmentExampleBinding
import ar.com.wolox.android.example.ui.viewpager.ViewPagerActivity
import ar.com.wolox.wolmo.core.fragment.WolmoFragment
import ar.com.wolox.wolmo.core.util.ToastFactory
import ar.com.wolox.wolmo.core.util.openBrowser
import ar.com.wolox.wolmo.core.util.openDial
import javax.inject.Inject

class ExampleFragment private constructor() : WolmoFragment<FragmentExampleBinding, ExamplePresenter>(), ExampleView {

    @Inject
    internal lateinit var toastFactory: ToastFactory

    override fun layout() = R.layout.fragment_example

    override fun init() {
        // Aca va logica de validacion de permisos de usuario
        presenter.autoLogin()
    }

    override fun setListeners() {
        with(binding) {
            etEmail.addTextChangedListener { presenter.onUsernameInputChanged(it.toString()) }
            woloxLink.setOnClickListener { presenter.onWoloxLinkClicked() }
            woloxPhone.setOnClickListener { presenter.onWoloxPhoneClicked() }
            loginButton.setOnClickListener {
                val email: String = binding.etEmail.text.toString()
                val password: String = binding.etPassword.text.toString()
                val emailPattern = """[a-zA-Z0-9._-]+@[a-z]+\.+[a-z]+"""

                if (email.isEmpty()) {
                    binding.etEmail.error = "Email requerido"
                    binding.etEmail.requestFocus()
                    return@setOnClickListener
                }

                if (password.isEmpty()) {
                    binding.etPassword.error = "Password requerido"
                    binding.etPassword.requestFocus()
                    return@setOnClickListener
                }

                if (!email.matches(emailPattern.toRegex())) {
                    binding.etEmail.error = "Email inválido"
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

    override fun showError(msg: String) {

        println("error deberia salir en pantalla")
        if (msg.isEmpty())
            toastFactory.show(R.string.unknown_error)
        else
            toastFactory.show(msg)
    }

    override fun openBrowser(url: String) = requireContext().openBrowser(url)

    override fun openPhone(woloxPhone: String) = requireContext().openDial(woloxPhone)

    companion object {
        fun newInstance() = ExampleFragment()
    }
}
