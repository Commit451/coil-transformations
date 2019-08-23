import dsl.Library
import dsl.compileSdk
import dsl.targetSdk
import dsl.versionCode
import dsl.versionName
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("org.jlleitschuh.gradle.ktlint")
}

android {
    compileSdkVersion(project.compileSdk)
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
}

dependencies {
    implementation(project(":transformations"))
    implementation(project(":transformations-gpu"))

    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    implementation(Library.ANDROIDX_APPCOMPAT)
    implementation(Library.ANDROIDX_CONSTRAINT_LAYOUT)
    implementation(Library.ANDROIDX_CORE)
    implementation(Library.ANDROIDX_LIFECYCLE_EXTENSIONS)
    implementation(Library.ANDROIDX_LIFECYCLE_LIVE_DATA)
    implementation(Library.ANDROIDX_LIFECYCLE_VIEW_MODEL)
    implementation(Library.ANDROIDX_RECYCLER_VIEW)

    implementation(Library.MATERIAL)
}
