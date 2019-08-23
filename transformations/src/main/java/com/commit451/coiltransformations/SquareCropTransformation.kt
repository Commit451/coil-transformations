package com.commit451.coiltransformations

import android.graphics.Bitmap
import coil.bitmappool.BitmapPool
import coil.transform.Transformation
import kotlin.math.max

/**
 * A [Transformation] that applies a crop square transformation.
 */
class SquareCropTransformation : Transformation {

    override fun key(): String = SquareCropTransformation::class.java.name

    override suspend fun transform(pool: BitmapPool, input: Bitmap): Bitmap {
        val size = max(input.width, input.height)
        return Util.centerCrop(pool, input, size, size)
    }
}
