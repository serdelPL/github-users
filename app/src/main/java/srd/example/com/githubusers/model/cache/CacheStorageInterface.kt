package srd.example.com.githubusers.model.cache

import io.reactivex.Single

interface CacheStorageInterface {
    fun save(json: String)
    fun getStoredList(): Single<String>
}
