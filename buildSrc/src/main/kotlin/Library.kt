@file:Suppress("unused")

package dsl

object Library {

    // CORE
    const val ANDROIDX_ANNOTATION = "androidx.annotation:annotation:1.1.0"
    const val ANDROIDX_APPCOMPAT = "androidx.appcompat:appcompat:1.0.2"
    const val ANDROIDX_CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:1.1.3"
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.0.2"
    const val ANDROIDX_MULTIDEX = "androidx.multidex:multidex:2.0.1"
    const val ANDROIDX_RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.0.0"

    private const val LIFECYCLE_VERSION = "2.0.0"
    const val ANDROIDX_LIFECYCLE_COMMON = "androidx.lifecycle:lifecycle-common-java8:$LIFECYCLE_VERSION"
    const val ANDROIDX_LIFECYCLE_EXTENSIONS = "androidx.lifecycle:lifecycle-extensions:$LIFECYCLE_VERSION"
    const val ANDROIDX_LIFECYCLE_LIVE_DATA = "androidx.lifecycle:lifecycle-livedata:$LIFECYCLE_VERSION"
    const val ANDROIDX_LIFECYCLE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"

    const val MATERIAL = "com.google.android.material:material:1.0.0"
}
