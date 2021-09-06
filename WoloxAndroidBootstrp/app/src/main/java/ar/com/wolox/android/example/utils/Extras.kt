package ar.com.wolox.android.example.utils

import android.app.Activity
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import ar.com.wolox.android.example.model.LoginUserData
import ar.com.wolox.android.example.model.News

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

    object Testing {
        const val EMAIL = "melvin.lambert15@example.com"
        const val PASSWORD = "123456"
        val CORRECT_USERDATA = LoginUserData(EMAIL, PASSWORD)

        const val INCORRECT_EMAIL = "melvin.lambert@example.com"
        const val INCORRECT_PASSWORD = "123"
        val INCORRECT_USERDATA = LoginUserData(INCORRECT_EMAIL, INCORRECT_PASSWORD)

        private val NEWS_LIST: List<Long> = listOf()
        val NEWS_MOCK = News(8, "someone", "something", "2018-02-16", "http://mypic.com", NEWS_LIST, "sometime", "another time")
    }
}
