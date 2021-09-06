package ar.com.wolox.android.example.ui.new

import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.model.NewsResponse
import ar.com.wolox.android.example.network.repository.NewsRepository
import ar.com.wolox.android.example.ui.news.NewsPresenter
import ar.com.wolox.android.example.ui.news.NewsView
import ar.com.wolox.android.example.utils.Extras.Testing.NEWS_MOCK
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
class NewsPresenterTest : WolmoPresenterTest<NewsView, NewsPresenter>() {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(runOnAllTests = true)

    @Mock
    lateinit var userSession: UserSession

    @Mock
    lateinit var newsRepository: NewsRepository

    override fun getPresenterInstance() = NewsPresenter(newsRepository, userSession)

    @Test
    fun `on init`() {

        presenter.onInit(NEWS_MOCK)

        verify(view, times(1)).setUpUi(NEWS_MOCK)
    }

    @Test
    fun `successfully setting like`() = runBlocking {

        // GIVEN
        val response = mock(Response::class.java) as Response<NewsResponse>
        whenever(newsRepository.setLike()).doReturn(NetworkResponse.Success(response))

        // WHEN
        presenter.onLikeButtonClicked().join()
    }

    @Test
    fun `Error while setting like`() = runBlocking {

        // GIVEN
        val response = mock(Response::class.java) as Response<NewsResponse>
        whenever(newsRepository.setLike()).doReturn(NetworkResponse.Error(response))

        // WHEN
        presenter.onLikeButtonClicked().join()

        // THEN
        verify(view, times(1)).showError(RequestCode.FAILED)
    }

    @Test
    fun `Failure while setting like`() = runBlocking {

        // GIVEN
        whenever(newsRepository.setLike()).doReturn(NetworkResponse.Failure(Throwable()))

        // WHEN
        presenter.onLikeButtonClicked().join()

        // THEN
        verify(view, times(1)).showError(RequestCode.FATALERROR)
    }

    @Test
    fun `successfully getting news detail`() = runBlocking {

        // GIVEN
        val response = mock(Response::class.java) as Response<News>
        whenever(newsRepository.getSingleNewsItem()).doReturn(NetworkResponse.Success(response))

        // WHEN
        presenter.refreshNewsItem().join()

        // THEN
        verify(view, times(1)).showLoader(false)
    }

    @Test
    fun `failed  gettting news detail response with error response`() = runBlocking {

        // GIVEN
        val response = mock(Response::class.java) as Response<News>
        whenever(newsRepository.getSingleNewsItem()).doReturn(NetworkResponse.Error(response))

        // WHEN
        presenter.refreshNewsItem().join()

        // THEN
        verify(view, times(1)).showLoader(false)
        verify(view, times(1)).showError(RequestCode.FAILED)
    }

    @Test
    fun `failed  getting news detail response with failure request`() = runBlocking {
        // GIVEN
        whenever(newsRepository.getSingleNewsItem()).doReturn(NetworkResponse.Failure(Throwable()))

        // WHEN
        presenter.refreshNewsItem().join()

        // THEN
        verify(view, times(1)).showLoader(false)
        verify(view, times(1)).showError(RequestCode.FATALERROR)
    }
}
