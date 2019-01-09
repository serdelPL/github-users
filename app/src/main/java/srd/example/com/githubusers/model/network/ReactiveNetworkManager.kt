package srd.example.com.githubusers.model.network

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.Single

class ReactiveNetworkManager : NetworkManagerInterface {
    override fun checkInternetConnection(): Single<Boolean> {
        return ReactiveNetwork.checkInternetConnectivity()
    }
}