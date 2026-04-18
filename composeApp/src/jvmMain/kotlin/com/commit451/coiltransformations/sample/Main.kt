package com.commit451.coiltransformations.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Coil Transformations Sample",
    ) {
        App()
    }
}
