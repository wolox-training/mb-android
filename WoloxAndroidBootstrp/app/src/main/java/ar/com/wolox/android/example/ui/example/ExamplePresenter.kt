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

        val loginUserData = LoginUserData(email, password)

        if (view?.isOnline() == true) {
            view?.showLoader(true)
            view?.let { onLoginRequest(loginUserData) }
        } else {
            view?.showError(RequestCode.NOCONNECTIVITY)
        }
    }

    private fun onLoginRequest(loginUserData: LoginUserData) = launch {
        networkRequest(loginRepository.loginPostRepo(loginUserData)) {
            onResponseSuccessful { _ ->
                userSession.apply {
                    username = loginUserData.email
                    password = loginUserData.password
                }
                view?.goToViewPager(loginUserData.email)
            }
            onResponseFailed { _, _ -> view?.showError(RequestCode.FAILED) }
            onCallFailure { view?.showError(RequestCode.FATALERROR) }
            view?.showLoader(false)
        }
    }

    fun onUsernameInputChanged(text: String) = view?.toggleLoginButtonEnable(text.isNotBlank())

    fun onWoloxLinkClicked() {

        view?.openBrowser(WOLOX_URL)
    }

    fun autoLogin() {

        if (userSession.username != null && userSession.password != null) {
            view?.goToViewPager("")
        }
    }

    companion object {
        private const val WOLOX_URL = "www.wolox.com.ar"
    }
}

enum class RequestCode {
    FAILED,
    FATALERROR,
    NOCONNECTIVITY,
}