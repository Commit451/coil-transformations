package com.commit451.coiltransformations

import coil3.Bitmap
import coil3.size.Size
import coil3.size.pxOrElse
import coil3.transform.Transformation
import kotlin.math.max

/**
 * A [Transformation] that applies a crop square transformation.
 */
class SquareCropTransformation : Transformation() {

    override val cacheKey: String = "SquareCropTransformation"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val largerSize = max(
            size.width.pxOrElse { input.widthPx },
            size.height.pxOrElse { input.heightPx },
        )
        return input.centerCrop(largerSize, largerSize)
    }
}
