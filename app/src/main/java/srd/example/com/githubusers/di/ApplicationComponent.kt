package srd.example.com.githubusers.di

import android.content.Context
import dagger.Component
import srd.example.com.githubusers.di.modules.ServicesModule
import srd.example.com.githubusers.model.cache.CacheStorageInterface
import srd.example.com.githubusers.model.GitHubUsersServiceInterface
import srd.example.com.githubusers.model.json.JsonConverterInterface
import srd.example.com.githubusers.model.rest.RestBuilderInterace

import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ServicesModule::class))
interface ApplicationComponent{
    fun getGithubService(): GitHubUsersServiceInterface
    fun getRestBuilder(): RestBuilderInterace
    fun getContext():Context
    fun getCahedStorage(): CacheStorageInterface
    fun getJsonConverter(): JsonConverterInterface
}
