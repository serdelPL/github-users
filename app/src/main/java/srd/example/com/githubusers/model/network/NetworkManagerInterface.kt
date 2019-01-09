package srd.example.com.githubusers.model.network

import io.reactivex.Single

interface NetworkManagerInterface {
    fun checkInternetConnection(): Single<Boolean>
}
