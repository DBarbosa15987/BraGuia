package com.example.braguia

import android.app.Application

class BraGuiaApplication : Application(){

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = BraGuiaAppContainer(this)
    }

}