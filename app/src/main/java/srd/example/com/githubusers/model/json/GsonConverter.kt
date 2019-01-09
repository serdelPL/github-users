package srd.example.com.githubusers.model.json

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import srd.example.com.githubusers.model.User
import java.util.*

class GsonConverter : JsonConverterInterface {
    override fun fromUserListToJson(list: List<User>): String {
        return GsonBuilder().create().toJson(list)
    }

    override fun fromJsonToUserList(json: String): List<User> {
        val listType = object : TypeToken<LinkedList<User>>() {}.type
        return Gson().fromJson(json, listType)
    }
}