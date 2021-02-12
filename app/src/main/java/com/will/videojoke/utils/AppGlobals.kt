package com.will.videojoke.utils

import android.app.Application
import java.lang.reflect.InvocationTargetException

object AppGlobals {
    private var sApplication: Application? = null
    public fun getApplication(): Application? {
        if (null == sApplication) {

                val method =
                    Class.forName("android.app.ActivityThread")
                        .getDeclaredMethod("currentApplication")
                    sApplication = method.invoke(null) as Application
        }
        return sApplication
    }
}