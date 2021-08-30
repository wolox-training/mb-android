package ar.com.wolox.android.example

import ar.com.wolox.android.BuildConfig
import ar.com.wolox.android.example.di.DaggerAppComponent
import ar.com.wolox.android.example.utils.Extras.UserLogin.HEADER_ACCESS_TOKEN
import ar.com.wolox.android.example.utils.Extras.UserLogin.HEADER_CLIENT
import ar.com.wolox.android.example.utils.Extras.UserLogin.HEADER_UID
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.wolmo.core.WolmoApplication
import ar.com.wolox.wolmo.networking.di.DaggerNetworkingComponent
import ar.com.wolox.wolmo.networking.di.NetworkingComponent
import com.google.gson.FieldNamingPolicy
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.leakcanary.LeakCanary
import dagger.android.AndroidInjector
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import javax.inject.Inject

class BootstrapApplication : WolmoApplication() {
    @Inject
    lateinit var userSession: UserSession

    override fun onInit() {
        // Initialize Application stuff here
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    override fun applicationInjector(): AndroidInjector<BootstrapApplication> {
        return DaggerAppComponent.builder().networkingComponent(buildDaggerNetworkingComponent())
                .sharedPreferencesName(BaseConfiguration.SHARED_PREFERENCES_NAME).application(this)
                .create(this)
    }

    private fun buildDaggerNetworkingComponent(): NetworkingComponent {
        val builder = DaggerNetworkingComponent.builder().baseUrl(
                BaseConfiguration.EXAMPLE_CONFIGURATION_KEY)
                .gsonNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)

        if (BuildConfig.DEBUG) {
            builder.okHttpInterceptors(buildHttpLoggingInterceptor(Level.BODY),
                ChuckInterceptor(this), headersInterceptor())
        }
        return builder.build()
    }

    private fun headersInterceptor() = Interceptor { chain ->
        with(BaseConfiguration) {
            val request = chain.request().let {
                if (userSession.isOngoingSession == true) {
                    it.newBuilder()
                        .addHeader(HEADER_ACCESS_TOKEN, userSession.accessToken!!)
                        .addHeader(HEADER_CLIENT, userSession.client!!)
                        .addHeader(HEADER_UID, userSession.uid!!)
                        .build()
                } else {
                    it
                }
            }
            val response = chain.proceed(request)
            if (!response.header(HEADER_ACCESS_TOKEN).isNullOrEmpty()) {
                userSession.accessToken = response.header(HEADER_ACCESS_TOKEN)
            }
            if (!response.header(HEADER_CLIENT).isNullOrEmpty()) {
                userSession.client = response.header(HEADER_CLIENT)
            }
            if (!response.header(HEADER_UID).isNullOrEmpty()) {
                userSession.uid = response.header(HEADER_UID)
            }
            response
        }
    }

    /**
     * Returns a [HttpLoggingInterceptor] with the newLevel given by **newLevel**.
     *
     * @param newLevel - Logging newLevel for the interceptor.
     * @return New instance of interceptor
     */
    private fun buildHttpLoggingInterceptor(newLevel: HttpLoggingInterceptor.Level): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply { this.level = newLevel }
    }
}
