package srd.example.com.githubusers.model

import java.net.URI

data class User (val login: String = "", val imgUrl: URI = URI(""),val id: String="")

data class UserDetails(val login: String = "",
                       val imgUrl: URI = URI(""),
                       val profileUrl: URI = URI(""),
                       val reposUrl: URI = URI(""),
                       val followersUrl: URI = URI(""))
