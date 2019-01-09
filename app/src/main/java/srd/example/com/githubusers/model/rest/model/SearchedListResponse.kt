package srd.example.com.githubusers.model.rest.model

data class SearchedListResponse(val items : List<UserDao> = emptyList())

data class UserDao(val login:String = "",
                   val id:String="",
                   val avatar_url:String ="",
                   val followers_url:String ="",
                   val repos_url:String ="",
                   val url:String ="")

