package srd.example.com.githubusers.ui.userdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.trello.rxlifecycle2.LifecycleTransformer
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.users_details_fragment.*
import srd.example.com.githubusers.R
import srd.example.com.githubusers.model.UserDetails
import srd.example.com.githubusers.ui.MainActivity
import javax.inject.Inject

class UsersDetailsFragment : RxFragment(), UserDetailsViewInterface {

    companion object {
        private const val USER_ID_BUNDLE_KEY = "userId"

        fun newInstance(userid: String = ""): UsersDetailsFragment {
            val usersDetailsFragment = UsersDetailsFragment()
            val args = Bundle()
            args.putString(USER_ID_BUNDLE_KEY, userid)
            usersDetailsFragment.arguments = args
            return usersDetailsFragment
        }
    }

    @Inject
    lateinit var presenter: UserDetailsPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.users_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).activityComponent.inject(this)

        presenter.bindView(this)
        presenter.userId= arguments?.getString(USER_ID_BUNDLE_KEY,"") ?: ""

    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun loadUser(user: UserDetails) {
        Picasso.get().load(user.imgUrl.toString())
            .placeholder(android.R.drawable.ic_dialog_info)
            .error(android.R.drawable.ic_delete)
            .into(avatar)
        login.text=user.login
        reposUrl.text=user.reposUrl.toString()
        profileUrl.text=user.profileUrl.toString()
        errorMsg.visibility=GONE
    }

    override fun failedToDownload() {
        errorMsg.visibility= VISIBLE
    }

    override fun invalidUserSelected() {
        selectValidUser.visibility= VISIBLE
    }

    override fun <T> lifecycleBinder(): LifecycleTransformer<T> = bindToLifecycle()

    override fun observerScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun toggleLoadingVisiblity(visible: Boolean) {
        progressBar.visibility= if(visible) View.VISIBLE else View.GONE
    }
}
