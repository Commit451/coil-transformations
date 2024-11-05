import coiltransformations.coilVersion
import coiltransformations.minSdk
import coiltransformations.targetSdk

plugins {
    id("com.android.library")
    id("kotlin-android")
}

apply(from = "../publish.gradle")

android {
    namespace = "com.commit451.coiltransformations.facedetection"
    compileSdk = project.targetSdk
    defaultConfig {
        minSdk = project.minSdk
        targetSdk = project.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    api("io.coil-kt.coil3:coil-core:${project.coilVersion}")
    api("com.google.mlkit:face-detection:16.1.7")
}
