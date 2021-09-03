package ar.com.wolox.android.example.ui.example

import ar.com.wolox.android.example.model.LoginResponse
import ar.com.wolox.android.example.network.repository.LoginRepository
import ar.com.wolox.android.example.utils.Extras
import ar.com.wolox.android.example.utils.RequestCode
import ar.com.wolox.android.example.utils.UserSession
import ar.com.wolox.wolmo.core.tests.CoroutineTestRule
import ar.com.wolox.wolmo.core.tests.WolmoPresenterTest
import ar.com.wolox.wolmo.networking.retrofit.handler.NetworkResponse
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class ExamplePresenterTest : WolmoPresenterTest<ExampleView, ExamplePresenter>() {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(runOnAllTests = true)

    @Mock
    lateinit var userSession: UserSession

    @Mock
    lateinit var loginRepository: LoginRepository

    override fun getPresenterInstance() = ExamplePresenter(userSession, loginRepository)

    @Test
    fun `success's login`() = runBlocking {

        // GIVEN
        val userLoginData = Extras.Testing.CORRECT_USERDATA
        val response = mock(Response::class.java) as Response<LoginResponse>
        whenever(loginRepository.loginPostRepo(userLoginData)).doReturn(NetworkResponse.Success(response))

        // WHEN
        presenter.onLoginRequest(userLoginData).join()

        // THEN
        verify(userSession, times(1)).username = userLoginData.email
        verify(userSession, times(1)).password = userLoginData.password
        verify(view, times(1)).goToViewPager(Extras.Testing.EMAIL)
    }

    @Test
    fun `Error with incorrect credentials login attempt`() = runBlocking {

        // GIVEN
        val userLoginData = Extras.Testing.INCORRECT_USERDATA
        val response = mock(Response::class.java) as Response<LoginResponse>
        whenever(loginRepository.loginPostRepo(userLoginData)).doReturn(NetworkResponse.Error(response))

        // WHEN
        presenter.onLoginRequest(userLoginData).join()

        // THEN
        verify(view, times(1)).showError(RequestCode.FAILED)
    }

    @Test
    fun `Failed login attempt`() = runBlocking {

        // GIVEN
        val userLoginData = Extras.Testing.CORRECT_USERDATA
        whenever(loginRepository.loginPostRepo(userLoginData)).doReturn(NetworkResponse.Failure(Throwable()))

        // WHEN
        presenter.onLoginRequest(userLoginData).join()

        // THEN
        verify(view, times(1)).showError(RequestCode.FATALERROR)
    }

    @Test
    fun `given an user and a color when login button is clicked then user session should be updated`() = runBlocking {

        // GIVEN
        val user = "Test"
        val color = "_"

        // WHEN
        presenter.onLoginButtonClicked(user, color)

        // THEN
        verify(userSession, times(1)).username = user
    }

    @Test
    fun `given an user and a color when login button is clicked then view should go to viewpager`() {

        // GIVEN
        val user = "Test"
        val color = "blue"

        // WHEN
        presenter.onLoginButtonClicked(user, color)

        // THEN
        verify(view, times(1)).goToViewPager(color)
    }
}
