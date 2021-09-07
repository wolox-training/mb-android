package ar.com.wolox.android.example.ui.example
import android.util.Log
import ar.com.wolox.android.example.model.LoginUserData
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.wolmo.core.presenter.CoroutineBasePresenter
import ar.com.wolox.android.example.network.builder.networkRequest
import ar.com.wolox.android.example.network.repository.LoginRepository
import ar.com.wolox.android.example.utils.RequestCode
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExamplePresenter @Inject constructor(private val userSession: UserSession, private val loginRepository: LoginRepository) : CoroutineBasePresenter<ExampleView>() {

    fun onLoginButtonClicked(email: String, password: String) {

        val loginUserData = LoginUserData(email, password)

        if (view?.isOnline() == true) {
            userSession.isOngoingSession = false
            view?.showLoader(true)
            view?.let { onLoginRequest(loginUserData) }
        } else {
            view?.showError(RequestCode.NOCONNECTIVITY)
        }
    }

    fun onLoginRequest(loginUserData: LoginUserData) = launch {

        networkRequest(loginRepository.loginPostRepo(loginUserData)) {
            onResponseSuccessful { response ->

                userSession.apply {
                    username = loginUserData.email
                    password = loginUserData.password
                    val userId = response?.data?.id?.toInt()
                    id = userId
                    isOngoingSession = true
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

    fun firebasePushNotifications() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d("TAG", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log token
            Log.d("TAG", token)
        })
    }

    companion object {
        private const val WOLOX_URL = "www.wolox.com.ar"
    }
}
