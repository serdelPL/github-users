package srd.example.com.githubusers.ui

abstract class BasePresenter<T : BaseView> {
    protected lateinit var view: T

    abstract fun onStart()
    abstract fun onStop()

    fun bindView(view: T){
        this.view=view
    }
}
