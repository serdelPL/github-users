package srd.example.com.githubusers

import android.app.Application
import android.content.Context
import srd.example.com.githubusers.di.ApplicationComponent
import srd.example.com.githubusers.di.DaggerApplicationComponent
import srd.example.com.githubusers.di.modules.ServicesModule

class AppApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
            .builder()
            .servicesModule(ServicesModule(this))
            .build()
    }
    companion object {
        fun getApplication(context: Context): AppApplication {
            return context.applicationContext as AppApplication
        }
    }
}