package ar.com.wolox.android.example.ui.example
import ar.com.wolox.android.example.model.LoginUserData
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.wolmo.core.presenter.CoroutineBasePresenter
import ar.com.wolox.android.example.network.builder.networkRequest
import ar.com.wolox.android.example.network.repository.LoginRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExamplePresenter @Inject constructor(private val userSession: UserSession, private val loginRepository: LoginRepository) : CoroutineBasePresenter<ExampleView>() {

    fun onLoginButtonClicked(email: String, password: String) {

        println("Cargando...")

        val loginUserData = LoginUserData(email, password)

        view?.showLoader(true)

        view?.let { onLoginRequest(loginUserData) }
    }

    private fun onLoginRequest(loginUserData: LoginUserData) = launch {
        println("Realizando request...")
        networkRequest(loginRepository.loginPostRepo(loginUserData)) {
            onResponseSuccessful { response ->

                println("Response: ${response?.data}")
                userSession.apply {
                    username = loginUserData.email
                    this.password = loginUserData.password
                }
                view?.goToViewPager(loginUserData.email)
            }
            onResponseFailed { _, _ -> view?.showError("Invalid login credentials. Please try again.") }
            onCallFailure { view?.showError("Whoops! Something went wrong") }
            view?.showLoader(false)
        }
    }

    fun onUsernameInputChanged(text: String) = view?.toggleLoginButtonEnable(text.isNotBlank())

    fun onWoloxLinkClicked() = view?.openBrowser(WOLOX_URL)

    fun onWoloxPhoneClicked() = view?.openPhone(WOLOX_PHONE)

    fun autoLogin() {
        println("Gettin user data")

        if (userSession.username != null && userSession.password != null) {
            println("username: ${userSession.username}")
            println("username: ${userSession.password}")
            view?.goToViewPager("")
        }
    }

    companion object {
        private const val WOLOX_URL = "www.wolox.com.ar"
        private const val WOLOX_PHONE = "08001234567"
    }
}
