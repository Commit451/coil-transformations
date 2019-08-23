plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(29)
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
    api("jp.co.cyberagent.android:gpuimage:2.0.3")
}

apply("https://raw.githubusercontent.com/Commit451/gradle-android-javadocs/1.0.0/gradle-android-javadocs.gradle")
