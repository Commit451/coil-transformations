buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:9.2.1")
        classpath("com.github.dcendents:android-maven-gradle-plugin:2.1")
        classpath(kotlin("gradle-plugin", version = "1.4.10"))
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}
