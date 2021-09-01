package ar.com.wolox.android.example.utils

import ar.com.wolox.wolmo.core.di.scopes.ApplicationScope
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager

import javax.inject.Inject

@ApplicationScope
class UserSession @Inject constructor(private val sharedPreferencesManager: SharedPreferencesManager) {

    var isOngoingSession: Boolean? = null
        get() = field ?: sharedPreferencesManager.get(Extras.UserLogin.IS_ON_GOING_SESSION, false)
        set(isOngoingSession) {
            field = isOngoingSession
            if (isOngoingSession != null) {
                sharedPreferencesManager.store(Extras.UserLogin.IS_ON_GOING_SESSION, isOngoingSession)
            }
        }

    // Really, we don't need to query the username because this instance live as long as the
    // application, but we should add a check in case Android decides to kill the application
    // and return to a state where this isn't initialized.
    var username: String? = null
        get() = field ?: sharedPreferencesManager[Extras.UserLogin.USERNAME, null].also {
            field = it
        }
        set(username) {
            field = username
            sharedPreferencesManager.store(Extras.UserLogin.USERNAME, username)
        }

    var password: String? = null
        get() = field ?: sharedPreferencesManager[Extras.UserLogin.PASSWORD, null].also {
            field = it
        }
        set(password) {
            field = password
            sharedPreferencesManager.store(Extras.UserLogin.PASSWORD, password)
        }

    var accessToken: String? = null
        get() = field ?: sharedPreferencesManager[Extras.UserLogin.HEADER_ACCESS_TOKEN, null].also {
            field = it
        }
        set(accessToken) {
            field = accessToken
            sharedPreferencesManager.store(Extras.UserLogin.HEADER_ACCESS_TOKEN, accessToken)
        }

    var client: String? = null
        get() = field ?: sharedPreferencesManager[Extras.UserLogin.HEADER_CLIENT, null].also {
            field = it
        }
        set(client) {
            field = client
            sharedPreferencesManager.store(Extras.UserLogin.HEADER_CLIENT, client)
        }

    var uid: String? = null
        get() = field ?: sharedPreferencesManager[Extras.UserLogin.HEADER_UID, null].also {
            field = it
        }
        set(uid) {
            field = uid
            sharedPreferencesManager.store(Extras.UserLogin.HEADER_UID, uid)
        }

    var id: Int? = null
        get() = field ?: sharedPreferencesManager[Extras.UserLogin.USER_ID, 0]
        set(id) {
            field = id
            id?.let { sharedPreferencesManager.store(Extras.UserLogin.USER_ID, it) }
        }
}
