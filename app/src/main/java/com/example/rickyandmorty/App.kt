package com.example.rickyandmorty

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.rickyandmorty.data.local.DataBaseProvider
import com.example.rickyandmorty.data.local.LocalInjector
import com.example.rickyandmorty.data.remote.NetworkManager
import com.example.rickyandmorty.data.remote.RemoteInjector

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
        LocalInjector.appDatabase = DataBaseProvider.getInstance(this)
        RemoteInjector.apiService = NetworkManager.getInstance()
    }
    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }
}