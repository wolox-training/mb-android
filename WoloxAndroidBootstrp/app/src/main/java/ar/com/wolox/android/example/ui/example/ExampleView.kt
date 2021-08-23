package ar.com.wolox.android.example.ui.example

interface ExampleView {

    fun goToViewPager(favouriteColor: String)

    fun showLoader(isVisible: Boolean)

    fun openBrowser(url: String)

    fun showError(msg: String)

    fun toggleLoginButtonEnable(isEnable: Boolean)

    fun openPhone(woloxPhone: String): Any
}
