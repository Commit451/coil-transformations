buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.2.0")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.3.0")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.19.0")
        classpath(kotlin("gradle-plugin", version = "1.6.20"))
    }
}

plugins {
    id("com.github.ben-manes.versions") version "0.42.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
