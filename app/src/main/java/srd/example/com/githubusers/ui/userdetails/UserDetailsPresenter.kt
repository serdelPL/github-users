package srd.example.com.githubusers.ui.userdetails

import android.annotation.SuppressLint
import io.reactivex.schedulers.Schedulers
import srd.example.com.githubusers.model.GitHubUsersServiceInterface
import srd.example.com.githubusers.model.UserDetails
import srd.example.com.githubusers.ui.BasePresenter

class UserDetailsPresenter( private val gitHubUsersService: GitHubUsersServiceInterface ): BasePresenter<UserDetailsViewInterface>() {

    var userId: String = ""

    //as we use AndroidRxLifecycle we don't need to keep subscription reference
    @SuppressLint("CheckResult")
    override fun onStart() {
        if (userId.isNotEmpty()) {
            view.toggleLoadingVisiblity(true)
            gitHubUsersService.getUserById(userId)
                .compose(view.lifecycleBinder())
                .subscribeOn(Schedulers.io())
                .observeOn(view.observerScheduler())
                .subscribe({ userDetailsLoaded(it) }, {onError(it) })
        }else{
            view.invalidUserSelected()
        }
    }

    private fun userDetailsLoaded(userDetails: UserDetails) {
        view.toggleLoadingVisiblity(false)
        view.loadUser(userDetails)
    }

    private fun onError(throwable : Throwable) {
        throwable.printStackTrace()
        view.toggleLoadingVisiblity(false)
        view.failedToDownload()
    }
    override fun onStop() {}

}
