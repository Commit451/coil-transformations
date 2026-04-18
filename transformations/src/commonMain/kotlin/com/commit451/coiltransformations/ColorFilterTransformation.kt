package com.commit451.coiltransformations

import coil3.Bitmap
import coil3.size.Size
import coil3.transform.Transformation

/**
 * A [Transformation] that applies a color filter to an image.
 *
 * @param color the ARGB color filter to apply. Typically, you would want this to have an alpha
 *  component. Same packing as Android's `@ColorInt`: 0xAARRGGBB as an Int.
 */
class ColorFilterTransformation(
    private val color: Int,
) : Transformation() {

    override val cacheKey: String = "ColorFilterTransformation-$color"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        return input.applyColorFilter(color)
    }
}
