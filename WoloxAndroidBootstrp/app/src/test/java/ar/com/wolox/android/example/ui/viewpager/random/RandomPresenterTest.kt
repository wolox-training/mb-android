package ar.com.wolox.android.example.ui.viewpager.random

import ar.com.wolox.android.example.model.News
import ar.com.wolox.android.example.model.NewsResponse
import ar.com.wolox.android.example.network.repository.NewsRepository
import ar.com.wolox.android.example.utils.RequestCode
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
import java.util.ArrayList

@ExperimentalCoroutinesApi
class RandomPresenterTest : WolmoPresenterTest<RandomView, RandomPresenter>() {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule(runOnAllTests = true)

    @Mock
    lateinit var newsRepository: NewsRepository

    override fun getPresenterInstance() = RandomPresenter(newsRepository)

    @Test
    fun `successfully getting news response`() = runBlocking {

        // GIVEN
        val isOnRefresh = false
        val response = mock(Response::class.java) as Response<NewsResponse>
        whenever(newsRepository.getNews()).doReturn(NetworkResponse.Success(response))

        // WHEN
        presenter.getNewsRequest(isOnRefresh).join()

        // THEN
        (response.body()?.page as? ArrayList<News>?).let {
            if (it != null) {
                verify(view, times(1)).getNews(it)
            }
        }
    }

    @Test
    fun `failed news response with error response`() = runBlocking {

        val isOnRefresh = false
        val response = mock(Response::class.java) as Response<NewsResponse>
        whenever(newsRepository.getNews()).doReturn(NetworkResponse.Error(response))

        // WHEN
        presenter.getNewsRequest(isOnRefresh).join()

        // THEN
        verify(view, times(1)).showError(RequestCode.FAILED)
    }

    @Test
    fun `failed news response with failure request`() = runBlocking {

        val isOnRefresh = false
        whenever(newsRepository.getNews()).doReturn(NetworkResponse.Failure(Throwable()))

        // WHEN
        presenter.getNewsRequest(isOnRefresh).join()

        // THEN
        verify(view, times(1)).showError(RequestCode.FATALERROR)
    }
}
