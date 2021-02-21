package com.will.videojoke

import android.app.Application
import com.will.libnetwork.ApiService

class VideoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        ApiService.init<Any>("http//192.168.1.101:8080/serverdemo",null)
        ApiService.init<Any>("http://123.56.232.18:8080/serverdemo",null)
    }
}