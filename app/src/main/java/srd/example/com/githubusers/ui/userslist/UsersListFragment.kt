package srd.example.com.githubusers.ui.userslist

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import srd.example.com.githubusers.R

class UsersListFragment : Fragment() {

    companion object {
        fun newInstance() = UsersListFragment()
    }

    private lateinit var viewModel: UsersListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UsersListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
