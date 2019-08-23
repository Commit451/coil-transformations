package com.commit451.coiltransformations.sample

import androidx.annotation.DrawableRes
import coil.transform.Transformation

data class Image(
    val transformation: Transformation,
    val name: String = transformation::class.java.simpleName,
    @DrawableRes val resource: Int = R.drawable.demo,
    val additionalTransformation: Transformation? = null
)
