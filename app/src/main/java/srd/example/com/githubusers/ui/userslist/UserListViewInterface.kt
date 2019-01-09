package srd.example.com.githubusers.ui.userslist

import com.trello.rxlifecycle2.LifecycleTransformer
import io.reactivex.Scheduler
import srd.example.com.githubusers.model.User
import srd.example.com.githubusers.ui.BaseView

interface UserListViewInterface: BaseView {
    fun updateList(list: List<User>)
    fun <T> lifecycleBinder(): LifecycleTransformer<T>
    fun observerScheduler(): Scheduler
    fun toggleLoadingVisiblity(visible: Boolean)
}