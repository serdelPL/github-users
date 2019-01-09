package srd.example.com.githubusers.ui.userslist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.jakewharton.rxbinding2.widget.RxTextView
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.users_list_fragment.*
import srd.example.com.githubusers.R
import srd.example.com.githubusers.model.User
import srd.example.com.githubusers.ui.MainActivity
import javax.inject.Inject

class UsersListFragment : RxFragment(), UserListViewInterface {

    companion object {
        fun newInstance() = UsersListFragment()
    }

    private lateinit var userListAdapter: UserListAdapter

    @Inject
    lateinit var presenter: UserListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity
        mainActivity.activityComponent.inject(this)

        userListAdapter= UserListAdapter(itemSelected = mainActivity)
        userListRecycler.layoutManager=LinearLayoutManager(context)
        userListRecycler.adapter= userListAdapter

        presenter.bindView(this)
        presenter.searchSource(RxTextView.afterTextChangeEvents(searchBox).map { it.view().text.toString() })
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun updateList(list: List<User>) {
        if (list.isNotEmpty()) {
            userListAdapter.dataset=list
            userListAdapter.notifyDataSetChanged()
            emptyListInfo.visibility= INVISIBLE
        }else{
            userListRecycler.visibility=INVISIBLE
            emptyListInfo.visibility= VISIBLE
        }
    }

    override fun <T> lifecycleBinder(): LifecycleTransformer<T> = bindToLifecycle()

    override fun observerScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun toggleLoadingVisiblity(visible: Boolean) {
        loadingBar.visibility= if(visible) View.VISIBLE else View.GONE
    }
}
