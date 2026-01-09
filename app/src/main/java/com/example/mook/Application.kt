package com.example.mook

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MookApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // Initializations here
    }
}