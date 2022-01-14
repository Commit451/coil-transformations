buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
        classpath(kotlin("gradle-plugin", version = "1.6.10"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
