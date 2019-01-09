package srd.example.com.githubusers.ui.userslist

import srd.example.com.githubusers.model.User

interface UserSelected {
    fun onUserSelecter(user: User)
}