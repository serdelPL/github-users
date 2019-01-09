package srd.example.com.githubusers.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.main_activity.*
import srd.example.com.githubusers.AppApplication
import srd.example.com.githubusers.R
import srd.example.com.githubusers.model.User
import srd.example.com.githubusers.ui.di.ActivityComponent
import srd.example.com.githubusers.ui.di.DaggerActivityComponent
import srd.example.com.githubusers.ui.di.modules.PresenterModule
import srd.example.com.githubusers.ui.userdetails.UsersDetailsFragment
import srd.example.com.githubusers.ui.userslist.UserSelected
import srd.example.com.githubusers.ui.userslist.UsersListFragment

class MainActivity : AppCompatActivity(), UserSelected {

    internal lateinit var activityComponent: ActivityComponent

    private var isTabletInLandscape: Boolean = false
    private var selectedUserid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        daggerInit()

        isTabletInLandscape = rightContainer != null

        if (savedInstanceState == null) {
            if (isTabletInLandscape) {
                loadFragment(UsersListFragment.newInstance(), R.id.leftContainer)
                loadFragment(UsersDetailsFragment.newInstance(), R.id.rightContainer)
            }else{
                loadFragment(UsersListFragment.newInstance(), R.id.leftContainer)
            }
            title = getString(R.string.user_ist)
        }else{
            supportFragmentManager.popBackStackImmediate(UsersListFragment::class.java.name,0)
            val userId = savedInstanceState.getString(SELECTED_USER_ID_BUNDLE_KEY, "")
            title = if (isTabletInLandscape) {
                loadFragment(UsersDetailsFragment.newInstance(userId), R.id.rightContainer)
                getString(R.string.user_ist)
            }else{
                loadFragment(UsersDetailsFragment.newInstance(userId), R.id.leftContainer)
                if (userId.isNotEmpty()) getString(R.string.user_details) else getString(R.string.user_ist)
            }
        }
    }

    private fun loadFragment(fragment: Fragment, fragmentContainerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(fragmentContainerId, fragment)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun onUserSelecter(user: User) {
        selectedUserid = user.id
        title = if (isTabletInLandscape) {
            loadFragment(UsersDetailsFragment.newInstance(selectedUserid), R.id.rightContainer)
            getString(R.string.user_ist)
        }else{
            loadFragment(UsersDetailsFragment.newInstance(selectedUserid), R.id.leftContainer)
            getString(R.string.user_details)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharSequence(SELECTED_USER_ID_BUNDLE_KEY, selectedUserid)
    }

    private fun daggerInit() {
        val applicationComponent = AppApplication.getApplication(this).applicationComponent
        activityComponent = DaggerActivityComponent
            .builder()
            .applicationComponent(applicationComponent)
            .presenterModule(PresenterModule())
            .build()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val timeToCloseApp = if(isTabletInLandscape) 1 else 0
        if(supportFragmentManager.backStackEntryCount <= timeToCloseApp){
            finish()
        }
    }

    companion object {
        private const val SELECTED_USER_ID_BUNDLE_KEY: String = "storedUserId"
    }
}
