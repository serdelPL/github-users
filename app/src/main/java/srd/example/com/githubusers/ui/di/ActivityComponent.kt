package srd.example.com.githubusers.ui.di

import dagger.Component
import srd.example.com.githubusers.di.ApplicationComponent
import srd.example.com.githubusers.ui.di.modules.PresenterModule
import srd.example.com.githubusers.ui.di.scopes.ActivityScope
import srd.example.com.githubusers.ui.userdetails.UsersDetailsFragment
import srd.example.com.githubusers.ui.userslist.UsersListFragment

@ActivityScope
@Component(modules = arrayOf(PresenterModule::class), dependencies = arrayOf(ApplicationComponent::class))
interface ActivityComponent {
    fun inject(fragment: UsersListFragment)
    fun inject(fragment: UsersDetailsFragment)

}
