package com.commit451.coiltransformations

import android.graphics.Bitmap
import coil.size.Size
import coil.transform.Transformation
import kotlin.math.max

/**
 * A [Transformation] that applies a crop square transformation.
 */
class SquareCropTransformation : Transformation {

    override val cacheKey: String = SquareCropTransformation::class.java.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val largerSize = max(input.width, input.height)
        return Util.centerCrop(input, largerSize, largerSize)
    }
}
