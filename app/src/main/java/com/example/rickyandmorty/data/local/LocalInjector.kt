package com.example.rickyandmorty.data.local

object LocalInjector {

    var appDatabase: AppDataBase? = null

    fun injectDb(): AppDataBase? {
        return appDatabase
    }
}