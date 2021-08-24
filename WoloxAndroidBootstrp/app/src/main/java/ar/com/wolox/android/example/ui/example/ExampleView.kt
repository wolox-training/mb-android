package ar.com.wolox.android.example.ui.example

interface ExampleView {

    fun goToViewPager(favouriteColor: String)

    fun showLoader(isVisible: Boolean)

    fun isOnline(): Boolean

    fun openBrowser(url: String)

    fun showError(requestCode: RequestCode)

    fun toggleLoginButtonEnable(isEnable: Boolean)
}
