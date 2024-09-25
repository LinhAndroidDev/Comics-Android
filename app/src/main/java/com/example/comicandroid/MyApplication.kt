package com.example.comicandroid

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        setUpScreen()
    }

    private fun setUpScreen() {
        resources.displayMetrics.run {
            screenWidth = this.widthPixels
            screenHeight = this.heightPixels
        }
    }
}