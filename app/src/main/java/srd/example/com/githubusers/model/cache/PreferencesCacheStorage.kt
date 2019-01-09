package srd.example.com.githubusers.model.cache

import android.content.Context
import io.reactivex.Single

class PreferencesCacheStorage(context: Context) :
    CacheStorageInterface {

    private val sharedPref = context.getSharedPreferences(PREFRENCES,Context.MODE_PRIVATE)

    override fun save(json: String) {
        with (sharedPref.edit()) {
        putString(USER_LIST, json)
    commit()
        }
    }

    override fun getStoredList(): Single<String> {
        return Single.fromCallable { sharedPref.getString(USER_LIST,"") }
    }

    companion object {
        private const val PREFRENCES = "appPreferences"
        private const val USER_LIST = "gitHubUserList"
    }
}