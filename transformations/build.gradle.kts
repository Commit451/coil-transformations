import dsl.compileSdk
import dsl.minSdk
import dsl.targetSdk

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(project.compileSdk)
    defaultConfig {
        minSdkVersion(project.minSdk)
        targetSdkVersion(project.targetSdk)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    libraryVariants.all {
        generateBuildConfigProvider?.configure { enabled = false }
    }
}

dependencies {
    api("io.coil-kt:coil:0.6.1")
}
