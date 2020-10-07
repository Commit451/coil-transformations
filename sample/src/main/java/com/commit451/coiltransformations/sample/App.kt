@file:Suppress("unused")

package com.commit451.coiltransformations.sample

import android.app.Application
import android.util.Log
import coil.Coil
import coil.ImageLoader
import coil.util.DebugLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader(::buildDefaultImageLoader)
    }

    private fun buildDefaultImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
                .logger(DebugLogger(level = Log.DEBUG))
                .availableMemoryPercentage(0.5)
                .bitmapPoolPercentage(0.5)
                .crossfade(true)
                .build()
    }
}
