buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.2")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.2.1")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.19.0")
        classpath(kotlin("gradle-plugin", version = "1.6.10"))
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
