package ar.com.wolox.android.example.utils

import android.app.Activity
import android.content.SharedPreferences
import androidx.fragment.app.Fragment

/**
 * Util class to store keys to use with [SharedPreferences] or as argument between
 * [Fragment] or [Activity].
 */
object Extras {

    object UserLogin {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val HEADER_ACCESS_TOKEN = "Access-Token"
        const val HEADER_CLIENT = "Client"
        const val HEADER_UID = "Uid"
        const val USER_ID = "id"
        const val IS_ON_GOING_SESSION = "IS_ON_GOING_SESSION"
    }

    object ViewPager {
        const val FAVOURITE_COLOR_KEY = "FAVOURITE_COLOR_KEY"
    }

    object News {
        const val KEY_NAME = "NEWS"
    }
}
