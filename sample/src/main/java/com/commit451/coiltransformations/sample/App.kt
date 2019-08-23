@file:Suppress("unused")

package com.commit451.coiltransformations.sample

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.util.CoilLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CoilLogger.setEnabled(true)
        Coil.setDefaultImageLoader(::buildDefaultImageLoader)
    }

    private fun buildDefaultImageLoader(): ImageLoader {
        return ImageLoader(this) {
            availableMemoryPercentage(0.5)
            bitmapPoolPercentage(0.5)
            crossfade(true)
        }
    }
}
