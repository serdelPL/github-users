package srd.example.com.githubusers.model

import io.reactivex.Single
import srd.example.com.githubusers.model.cache.CacheStorageInterface
import srd.example.com.githubusers.model.json.JsonConverterInterface
import srd.example.com.githubusers.model.network.NetworkManagerInterface
import srd.example.com.githubusers.model.rest.RestBuilderInterace
import srd.example.com.githubusers.model.rest.model.UserDao
import java.net.URI
import java.util.*

class GitHubUserService(private val restBuilder: RestBuilderInterace,
                        private val cacheStorage: CacheStorageInterface,
                        private val jsonCovnerter: JsonConverterInterface,
                        private val networkManager: NetworkManagerInterface
                        ):GitHubUsersServiceInterface {

    override fun getCachedUsers(userLogin: String): Single<List<User>> {
        return cacheStorage.getStoredList()
            .map { jsonCovnerter.fromJsonToUserList(it) }
            .map{it.filter { it.login.contains(userLogin) }}
    }

    override fun getUsers(userLogin: String): Single<List<User>> {
        val response =  if (userLogin.isEmpty()){
            restBuilder.githubApi().defaultList()
        }else{
            restBuilder.githubApi().searchedList("$userLogin in:login").map {  it.items }
        }

        return networkManager.checkInternetConnection()
            .flatMap {
                if (it){
                getOnlineUsers(response)
                        .onErrorResumeNext {
                            it.printStackTrace()
            getCachedUsers(userLogin)
                        }
                }
                else{
                    getCachedUsers(userLogin)
                }
            }
    }

    private fun getOnlineUsers(response: Single<List<UserDao>>): Single<List<User>> {
        return response
            .map(::mapDaoToAppModel)
            .doOnSuccess { cacheStorage.save(jsonCovnerter.fromUserListToJson(it)) }
    }

    private fun mapDaoToAppModel(daoList: List<UserDao>): List<User> {
        val mapped: LinkedList<User> = LinkedList()
        daoList.forEach {
            mapped.add(User(it.login, URI(it.avatar_url),it.id))
        }
        return mapped
    }

    override fun getUserById(id: String): Single<UserDetails> {
        return restBuilder.githubApi()
            .getUser(id)
            .map { UserDetails(it.login,URI(it.avatar_url),URI(it.url),URI(it.repos_url),URI(it.followers_url)) }
    }
}