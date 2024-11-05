buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", version = "1.9.20"))
        classpath("com.android.tools.build:gradle:8.7.2")
        classpath("com.vanniktech:gradle-maven-publish-plugin:0.19.0")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}
