package com.commit451.coiltransformations.sample

sealed class Screen {

    object List : Screen()

    data class Detail(val image: Image) : Screen()
}
