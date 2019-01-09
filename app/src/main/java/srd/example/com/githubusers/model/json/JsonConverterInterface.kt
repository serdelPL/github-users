package srd.example.com.githubusers.model.json

import srd.example.com.githubusers.model.User

interface JsonConverterInterface {
    fun fromUserListToJson(list: List<User>): String
    fun fromJsonToUserList(json: String):List<User>
}
