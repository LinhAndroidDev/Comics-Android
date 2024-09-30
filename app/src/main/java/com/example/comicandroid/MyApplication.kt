package com.example.comicandroid

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

class MyApplication : Application() {
    var currentActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()

        setUpScreen()

        // Đăng ký ActivityLifecycleCallbacks
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = activity
            }

            override fun onActivityStarted(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityResumed(activity: Activity) {
                currentActivity = activity
            }

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {}

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                if (currentActivity == activity) {
                    currentActivity = null
                }
            }
        })
    }

    private fun setUpScreen() {
        resources.displayMetrics.run {
            screenWidth = this.widthPixels
            screenHeight = this.heightPixels
            heightStatusBar = getStatusBarHeight(this@MyApplication)
        }
    }

    private fun getStatusBarHeight(c: Context): Int {
        val resourceId = c.resources
            .getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            c.resources.getDimensionPixelSize(resourceId)
        } else 0
    }
}