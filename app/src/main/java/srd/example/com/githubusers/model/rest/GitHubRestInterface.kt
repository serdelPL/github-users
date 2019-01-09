package srd.example.com.githubusers.model.rest

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import srd.example.com.githubusers.model.rest.model.SearchedListResponse
import srd.example.com.githubusers.model.rest.model.UserDao


interface GitHubRestInterface {

    @GET("search/users")
    fun searchedList(@Query("q",encoded = true) queryString: String): Single<SearchedListResponse>

    @GET("users")
    fun defaultList(): Single<List<UserDao>>

    @GET("user/{id}")
    fun getUser(@Path("id")id: String): Single<UserDao>
}