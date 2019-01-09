package srd.example.com.githubusers.model

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import srd.example.com.githubusers.model.cache.CacheStorageInterface
import srd.example.com.githubusers.model.json.GsonConverter
import srd.example.com.githubusers.model.network.NetworkManagerInterface
import srd.example.com.githubusers.model.rest.GitHubRestInterface
import srd.example.com.githubusers.model.rest.RestBuilderInterace
import srd.example.com.githubusers.model.rest.model.SearchedListResponse
import srd.example.com.githubusers.model.rest.model.UserDao

class GitHubUserServiceTest {

    private val mockGitHubRestApi = mock<GitHubRestInterface>()
    private val mockRestService = mock<RestBuilderInterace>{
        on{githubApi()} doReturn mockGitHubRestApi
    }
    private val mockCache = mock<CacheStorageInterface>()
    private val mockNetworkManager = mock<NetworkManagerInterface>{
        on{checkInternetConnection()} doReturn Single.just(true)
    }
    private val jsonConverter = GsonConverter()

    private var  testObject = GitHubUserService(mockRestService,mockCache,jsonConverter,mockNetworkManager)

    @Test
    fun shouldReturnDefaultListWhenSearchedLoginIsEmpty() {
        val expected = getMockedDefaultRestList()
        whenever(mockGitHubRestApi.defaultList()).thenReturn(Single.just(expected))
        whenever(mockGitHubRestApi.searchedList(any())).thenReturn(Single.just(SearchedListResponse(getMockedSearchedRestList())))

        testObject.getUsers("").test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { return@assertValue it.size== expected.size }
    }

    @Test
    fun shouldReturnSearchedListWhenSearchedLoginIsNotEmpty() {
        val expected = getMockedSearchedRestList()
        whenever(mockGitHubRestApi.defaultList()).thenReturn(Single.just(getMockedDefaultRestList()))
        whenever(mockGitHubRestApi.searchedList(any())).thenReturn(Single.just(SearchedListResponse(getMockedSearchedRestList())))

        testObject.getUsers("mockQuery").test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { return@assertValue it.size==expected.size }
    }

    @Test
    fun shouldReturnCachedResponseInOffline(){
        val expected = getMockCachedResponse()
        whenever(mockNetworkManager.checkInternetConnection()).thenReturn(Single.just(false))
        whenever(mockGitHubRestApi.defaultList()).thenReturn(Single.just(getMockedDefaultRestList()))
        whenever(mockCache.getStoredList()).thenReturn(Single.just(jsonConverter.fromUserListToJson(expected)))

        testObject.getUsers("").test()
            .assertNoErrors()
            .assertComplete()
            .assertValue { return@assertValue it.size==expected.size }
    }

    private fun getMockCachedResponse() = listOf(User("login1"), User("login2"))

    private fun getMockedDefaultRestList() = listOf(UserDao("login1"), UserDao("login2"), UserDao("login3"))

    private fun getMockedSearchedRestList()= listOf(UserDao("login1"), UserDao("login2"), UserDao("login3"), UserDao("login4"), UserDao("login5"))

}