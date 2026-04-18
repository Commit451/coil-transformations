package com.commit451.coiltransformations.sample

import android.app.Application

class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeSampleApplication(this)
    }
}
