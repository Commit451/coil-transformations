plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.commit451.coiltransformations.sample"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.commit451.coiltransformations.sample"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles("shrinker-rules.pro", "shrinker-rules-android.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":transformations"))
    implementation(project(":transformations-gpu"))
    implementation(project(":transformations-face-detection"))
    implementation(libs.coil.compose)

    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.extensions)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.recyclerview)

    implementation(libs.material)
}
