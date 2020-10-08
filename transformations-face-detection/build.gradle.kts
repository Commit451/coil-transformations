plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(30)
    defaultConfig {
        minSdkVersion(14)
        targetSdkVersion(30)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildToolsVersion = "30.0.2"
    libraryVariants.all {
        generateBuildConfigProvider?.configure { enabled = false }
    }
}

dependencies {
    api("io.coil-kt:coil:0.13.0")
    api("com.google.mlkit:face-detection:16.0.2")
}

apply("https://raw.githubusercontent.com/Commit451/gradle-android-javadocs/1.1.0/gradle-android-javadocs.gradle")
