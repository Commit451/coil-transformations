package com.commit451.coiltransformations

import android.graphics.Bitmap
import coil3.size.Size
import coil3.size.pxOrElse
import coil3.transform.Transformation
import kotlin.math.max

/**
 * A [Transformation] that applies a crop square transformation.
 */
class SquareCropTransformation : Transformation() {

    override val cacheKey: String = SquareCropTransformation::class.java.name

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val largerSize = max(
            size.width.pxOrElse { input.width },
            size.height.pxOrElse { input.height }
        )
        return Util.centerCrop(input, largerSize, largerSize)
    }
}
