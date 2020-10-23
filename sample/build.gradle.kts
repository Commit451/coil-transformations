import coiltransformations.coilVersion
import coiltransformations.targetSdk
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(project.targetSdk)
    defaultConfig {
        applicationId = "com.commit451.coiltransformations.sample"
        minSdkVersion(21)
        targetSdkVersion(project.targetSdk)
        versionCode = 1
        versionName = "1.0.0"
        resConfigs("en")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

val lifecycleVersion = "2.0.0"

dependencies {
    implementation(project(":transformations"))
    implementation(project(":transformations-gpu"))
    implementation(project(":transformations-face-detection"))
    implementation("io.coil-kt:coil:${project.coilVersion}")

    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.2")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.lifecycle:lifecycle-extensions:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    implementation("com.google.android.material:material:1.2.1")
}
