package srd.example.com.githubusers.ui.userdetails

import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Scheduler
import srd.example.com.githubusers.model.UserDetails
import srd.example.com.githubusers.ui.BaseView

interface UserDetailsViewInterface: BaseView{
    fun <T> lifecycleBinder(): LifecycleTransformer<T>
    fun observerScheduler(): Scheduler
    fun toggleLoadingVisiblity(visible: Boolean)
    fun loadUser(user: UserDetails)
    fun failedToDownload()
    fun invalidUserSelected()
}
