package ar.com.wolox.android.example.ui.example
import ar.com.wolox.android.example.model.LoginResponse
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.wolmo.core.presenter.CoroutineBasePresenter
import ar.com.wolox.android.example.network.repository.PostRepository
import ar.com.wolox.android.example.network.builder.networkRequest
import kotlinx.coroutines.launch

import javax.inject.Inject
// CoroutineBasePresenter  PostRepository

class ExamplePresenter @Inject constructor(private val userSession: UserSession, private val postRepository: PostRepository) : CoroutineBasePresenter<ExampleView>() {

    fun onLoginButtonClicked(email: String, password: String) {

        println("Cargando...")

        view?.let { onLoginRequest(email, password) }
    }

    fun onLoginRequest(email: String, password: String) = launch {
        println("Realizando request...")

        networkRequest(postRepository.loginPostRepo(email, password)) {
            onResponseSuccessful { response -> onSuccesResponse(response!!, email, password) }
            // onResponseFailed { response, _ -> println("Error :${response?.errors?.get(0)}") }
            onResponseFailed { _, _ -> view?.showError("Invalid login credentials. Please try again.") }
            onCallFailure { println("error feo feo") }
        }
    }

    private fun onSuccesResponse(response: LoginResponse, email: String, password: String) {
        println("Response: ${response.data}")

        userSession.username = email
        userSession.password = password

        view?.goToViewPager(email)
    }

    fun onUsernameInputChanged(text: String) = view?.toggleLoginButtonEnable(text.isNotBlank())

    fun onWoloxLinkClicked() = view?.openBrowser(WOLOX_URL)

    fun onWoloxPhoneClicked() = view?.openPhone(WOLOX_PHONE)

    companion object {
        private const val WOLOX_URL = "www.wolox.com.ar"
        private const val WOLOX_PHONE = "08001234567"
    }
}
