@file:Suppress("unused")

package dsl

import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.kotlin
import kotlin.math.pow

val Project.minSdk: Int
    get() = intProperty("minSdk")

val Project.targetSdk: Int
    get() = intProperty("targetSdk")

val Project.compileSdk: Int
    get() = intProperty("compileSdk")

val Project.groupId: String
    get() = stringProperty("GROUP")

val Project.versionName: String
    get() = stringProperty("VERSION_NAME")

val Project.versionCode: Int
    get() {
        return versionName
            .split('.')
            .map { it.toInt() }
            .reversed()
            .sumByIndexed { index, unit ->
                // 1.2.3 -> 102030
                (unit * 10.0.pow(2 * index + 1)).toInt()
            }
    }

private fun Project.intProperty(name: String): Int {
    return (property(name) as String).toInt()
}

private fun Project.stringProperty(name: String): String {
    return property(name) as String
}

private inline fun <T> List<T>.sumByIndexed(selector: (Int, T) -> Int): Int {
    var index = 0
    var sum = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}
