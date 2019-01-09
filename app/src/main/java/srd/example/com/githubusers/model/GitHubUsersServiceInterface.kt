package srd.example.com.githubusers.model

import io.reactivex.Single

interface GitHubUsersServiceInterface {
    fun getUsers(userLogin: String): Single<List<User>>
    fun getCachedUsers(userLogin: String): Single<List<User>>
    fun getUserById(id: String): Single<UserDetails>
}
