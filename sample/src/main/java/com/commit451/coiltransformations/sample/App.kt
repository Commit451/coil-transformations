@file:Suppress("unused")

package com.commit451.coiltransformations.sample

import android.app.Application
import android.util.Log
import coil.Coil
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.util.DebugLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Coil.setImageLoader {
            ImageLoader.Builder(this)
                .logger(DebugLogger(level = Log.DEBUG))
                .memoryCache(MemoryCache.Builder(this).maxSizePercent(0.5).build())
                .crossfade(true)
                .build()
        }
    }
}
