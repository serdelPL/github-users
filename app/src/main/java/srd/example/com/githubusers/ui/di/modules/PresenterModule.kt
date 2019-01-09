package srd.example.com.githubusers.ui.di.modules

import dagger.Module
import dagger.Provides
import srd.example.com.githubusers.model.GitHubUsersServiceInterface
import srd.example.com.githubusers.ui.di.scopes.ActivityScope
import srd.example.com.githubusers.ui.userdetails.UserDetailsPresenter
import srd.example.com.githubusers.ui.userslist.UserListPresenter

@Module
class PresenterModule {
    @Provides
    @ActivityScope
    fun proideListPresenter(gitHubService: GitHubUsersServiceInterface): UserListPresenter {
        return UserListPresenter(gitHubService)
    }

    @Provides
    @ActivityScope
    fun provideUserDetailsPresenter(gitHubService: GitHubUsersServiceInterface): UserDetailsPresenter {
        return UserDetailsPresenter(gitHubService)
    }
}
