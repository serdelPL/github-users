package srd.example.com.githubusers.ui.userslist

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import srd.example.com.githubusers.model.GitHubUsersServiceInterface
import srd.example.com.githubusers.model.User
import srd.example.com.githubusers.ui.BasePresenter
import java.util.concurrent.TimeUnit

class UserListPresenter(
    private val gitHubUsersService: GitHubUsersServiceInterface
    ):BasePresenter<UserListViewInterface>() {

    //as we use AndroidRxLifecycle we don't need to keep subscription reference
    @SuppressLint("CheckResult")
    override fun onStart() {
        view.toggleLoadingVisiblity(true)
        gitHubUsersService
            .getUsers("")
            .compose(view.lifecycleBinder())
            .subscribeOn(Schedulers.io())
            .observeOn(view.observerScheduler())
            .subscribe(::listUploaded, { it.printStackTrace() })
    }

    private fun listUploaded(list: List<User>) {
        view.toggleLoadingVisiblity(false)
        view.updateList(list)
    }

    override fun onStop() {
    }

    //as we use AndroidRxLifecycle we don't need to keep subscription reference
    @SuppressLint("CheckResult")
    fun searchSource(search: Observable<String>) {
        search
            .filter { !it.isEmpty() }
            .debounce (500,TimeUnit.MILLISECONDS)
            .flatMapSingle { gitHubUsersService.getUsers(it) }
            .compose ( view.lifecycleBinder() )
            .observeOn(view.observerScheduler())
            .onErrorResumeNext(gitHubUsersService.getCachedUsers("").toObservable())
            .subscribe(::listUploaded, { it.printStackTrace() })
    }

}