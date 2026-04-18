package com.commit451.coiltransformations

import coil3.Bitmap
import coil3.size.Size
import coil3.transform.Transformation

/**
 * A [Transformation] that converts an image to shades of gray.
 */
class GrayscaleTransformation : Transformation() {

    override val cacheKey: String = "GrayscaleTransformation"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        return input.applyGrayscale()
    }

    override fun equals(other: Any?) = other is GrayscaleTransformation

    override fun hashCode() = cacheKey.hashCode()

    override fun toString() = "GrayscaleTransformation()"
}
