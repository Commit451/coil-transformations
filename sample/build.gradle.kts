import coiltransformations.coilVersion
import coiltransformations.minSdk
import coiltransformations.targetSdk

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "com.commit451.coiltransformations.sample"
    compileSdk = project.targetSdk
    defaultConfig {
        applicationId = "com.commit451.coiltransformations.sample"
        minSdk = project.minSdk
        targetSdk = project.targetSdk
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

dependencies {
    implementation(project(":transformations"))
    implementation(project(":transformations-gpu"))
    implementation(project(":transformations-face-detection"))
    implementation("io.coil-kt.coil3:coil-compose:${project.coilVersion}")

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("com.google.android.material:material:1.12.0")
}
