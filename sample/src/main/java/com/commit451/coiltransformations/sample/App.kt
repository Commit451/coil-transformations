@file:Suppress("unused")

package com.commit451.coiltransformations.sample

import android.app.Application
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.request.crossfade
import coil3.util.DebugLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(this)
                .logger(DebugLogger())
                .crossfade(true)
                .build()
        }
    }
}
