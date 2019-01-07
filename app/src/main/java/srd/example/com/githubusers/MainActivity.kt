package srd.example.com.githubusers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import srd.example.com.githubusers.ui.userslist.UsersListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UsersListFragment.newInstance())
                .commitNow()
        }
    }

}
