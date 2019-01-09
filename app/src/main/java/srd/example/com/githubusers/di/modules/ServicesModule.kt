package srd.example.com.githubusers.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import srd.example.com.githubusers.AppApplication
import srd.example.com.githubusers.model.*
import srd.example.com.githubusers.model.cache.CacheStorageInterface
import srd.example.com.githubusers.model.cache.PreferencesCacheStorage
import srd.example.com.githubusers.model.json.GsonConverter
import srd.example.com.githubusers.model.json.JsonConverterInterface
import srd.example.com.githubusers.model.network.NetworkManagerInterface
import srd.example.com.githubusers.model.network.ReactiveNetworkManager
import srd.example.com.githubusers.model.rest.RestBuilderInterace
import srd.example.com.githubusers.model.rest.RetrofitRestBuilder
import javax.inject.Singleton

@Module
class ServicesModule(private val application:AppApplication){

    @Provides
    @Singleton
    fun providerRestServiceBuilder(): RestBuilderInterace = RetrofitRestBuilder()

    @Provides
    @Singleton
    fun provideGitHubServcie(
        restServiceBuilder: RestBuilderInterace,
        cahceStorage: CacheStorageInterface,
        jsonConverter: JsonConverterInterface,
        networkManager: NetworkManagerInterface
    ): GitHubUsersServiceInterface = GitHubUserService(restServiceBuilder,cahceStorage,jsonConverter,networkManager )

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideStorage(context: Context): CacheStorageInterface = PreferencesCacheStorage(context)

    @Provides
    @Singleton
    fun provideJsonConverter(): JsonConverterInterface = GsonConverter()

    @Provides
    @Singleton
    fun provideNetworkManager(): NetworkManagerInterface = ReactiveNetworkManager()

}
